package com.llm.agents.core.chain;

import com.llm.agents.core.agent.Agent;
import com.llm.agents.core.agent.Output;
import com.llm.agents.core.chain.event.OnErrorEvent;
import com.llm.agents.core.chain.event.OnFinishedEvent;
import com.llm.agents.core.chain.event.OnNodeFinishedEvent;
import com.llm.agents.core.chain.event.OnNodeStartEvent;
import com.llm.agents.core.chain.event.OnStartEvent;
import com.llm.agents.core.chain.event.OnStatusChangeEvent;
import com.llm.agents.core.chain.node.AgentNode;
import com.llm.agents.core.util.CollectionUtil;
import com.llm.agents.core.util.StringUtil;
import com.llm.agents.core.util.thread.NamedThreadPools;

import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @Author Devin
 * @Date 2024/11/19 22:53
 * @Description: Chain执行链
 **/
public class Chain extends ChainNode {
    public static final String CTX_EXEC_COUNT = "_exec_count"; //当前Chain中执行次数
    protected Map<Class<?>, List<ChainEventListener>> eventListeners = new HashMap<>(0); //事件回调监听对象
    protected List<ChainOutputListener> outputListeners = new ArrayList<>(); //结果回调监听对象
    protected List<ChainNode> nodes; //Chain中执行结点list
    protected List<ChainEdge> edges;
    protected ChainStatus status = ChainStatus.READY; //当前Chain中事件标识
    protected String message; //执行相关的message提示描述字段
    protected Chain parent; //父链
    protected List<Chain> children;//子链
    //执行线程池
    protected ExecutorService asyncNodeExecutors = NamedThreadPools.newFixedThreadPool("chain-executor");

    public Chain(){
        this.id = UUID.randomUUID().toString();
    }

    public Map<Class<?>,List<ChainEventListener>> getEventListeners(){
        return eventListeners;
    }

    public void setEventListeners(Map<Class<?>,List<ChainEventListener>> eventListeners){
        this.eventListeners = eventListeners;
    }

    public synchronized void registerEventListener(Class<? extends ChainEvent> eventClass,ChainEventListener listener){
        List<ChainEventListener> chainEventListeners = eventListeners.computeIfAbsent(eventClass, k -> new ArrayList<>());
        chainEventListeners.add(listener);
    }

    public synchronized void registerEventListener(ChainEventListener listener) {
        List<ChainEventListener> chainEventListeners = eventListeners.computeIfAbsent(ChainEvent.class, k -> new ArrayList<>());
        chainEventListeners.add(listener);
    }

    public synchronized void removeEventListener(ChainEventListener listener) {
        for (List<ChainEventListener> list : eventListeners.values()) {
            list.removeIf(item -> item == listener);
        }
    }

    public synchronized void removeEventListener(Class<? extends ChainEvent> eventClass, ChainEventListener listener) {
        List<ChainEventListener> list = eventListeners.get(eventClass);
        if (list != null && !list.isEmpty()) {
            list.removeIf(item -> item == listener);
        }
    }

    public List<ChainOutputListener> getOutputListeners() {
        return outputListeners;
    }

    public void setOutputListeners(List<ChainOutputListener> outputListeners) {
        this.outputListeners = outputListeners;
    }

    public void registerOutputListener(ChainOutputListener outputListener) {
        if (this.outputListeners == null) {
            this.outputListeners = new ArrayList<>();
        }
        this.outputListeners.add(outputListener);
    }

    public List<ChainNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ChainNode> chainNodes) {
        this.nodes = chainNodes;
    }
    private void addChild(Chain child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }


    public void addNode(ChainNode chainNode){
        if(nodes == null){
            this.nodes = new ArrayList<>();
        }

        if(chainNode instanceof ChainEventListener){
            registerEventListener((ChainEventListener) chainNode);
        }

        if(chainNode.getId() == null){
            chainNode.setId(UUID.randomUUID().toString());
        }

        if(chainNode instanceof Chain){
            ((Chain) chainNode).parent = this;
            this.addChild((Chain) chainNode);
        }

        nodes.add(chainNode);
    }

    public void addNode(Agent agent){
        addNode(new AgentNode(agent));
    }

    public ChainStatus getStatus(){
        return status;
    }

    public void setStatus(ChainStatus status) {
        ChainStatus before = this.status;
        this.status = status;

        if (before != status) {
            notifyEvent(new OnStatusChangeEvent(this.status, before));
        }
    }

    public Chain getParent() {
        return parent;
    }

    public void setParent(Chain parent) {
        this.parent = parent;
    }

    public List<Chain> getChildren() {
        return children;
    }

    public void setChildren(List<Chain> children) {
        this.children = children;
    }

    public void notifyEvent(ChainEvent event){
        for(Map.Entry<Class<?>,List<ChainEventListener>> entry:eventListeners.entrySet()){
            if (entry.getKey().isInstance(event)) {
                for (ChainEventListener chainEventListener : entry.getValue()) {
                    chainEventListener.onEvent(event, this);
                }
            }
        }
        if (parent != null) parent.notifyEvent(event);
    }

    public Object get(String key) {
        return this.memory.get(key);
    }

    public Object getGlobal(String key) {
        return this.memory.get(key);
    }

    @Override
    protected Map<String, Object> execute(Chain chain) {
        this.execute(parent.getMemory().getAll());
        return this.memory.getAll();
    }

    public void execute(Object variable){
        this.execute(Output.DEFAULT_VALUE_KEY, variable);
    }

    public void execute(String key,Object variable){
        Map<String,Object> variables = new HashMap<>(1);
        variables.put(key,variable);
        this.execute(variables);
    }

    protected void runInLifeCycle(Map<String,Object> variables,Runnable runnable){
        if (variables != null) {
            this.memory.putAll(variables);
        }
        try {
            ChainContext.setChain(this);
            notifyEvent(new OnStartEvent());
            try {
                setStatus(ChainStatus.RUNNING);
                runnable.run();
            } catch (Exception e) {
                setStatus(ChainStatus.ERROR);
                notifyEvent(new OnErrorEvent(e));
            }
            if (status == ChainStatus.RUNNING) {
                setStatus(ChainStatus.FINISHED_NORMAL);
            } else if (status == ChainStatus.ERROR) {
                setStatus(ChainStatus.FINISHED_ABNORMAL);
            }
        } finally {
            ChainContext.clearChain();
            notifyEvent(new OnFinishedEvent());
        }
    }

    private void notifyOutput(Agent agent,Object response){
        for(ChainOutputListener inputListener:outputListeners){
            inputListener.onOutput(this, agent, response);
        }
        if (parent != null) parent.notifyOutput(agent, response);
    }

    private ChainNode getNodeById(String id){
        if(id == null || StringUtil.noText(id)){
            return null;
        }
        for (ChainNode node:this.nodes){
            if(id.equals(node.getId())){
                return node;
            }
        }
        return null;
    }

    private List<ChainNode> getStartNodes(){
        if(this.nodes == null || this.nodes.isEmpty()){
            return null;
        }

        List<ChainNode> nodes = new ArrayList<>();

        for (ChainNode node : this.nodes) {
            if (CollectionUtil.noItems(node.getInwardEdges())) {
                nodes.add(node);
            }
        }

        return nodes;
    }

    private Map<String,Object> executeNode(ChainNode currentNode){
        Map<String, Object> executeResult = null;
        if (currentNode.isAsync()) {
            asyncNodeExecutors.execute(() ->
                    currentNode.execute(Chain.this)
            );
        } else {
            executeResult = currentNode.execute(this);
        }
        return executeResult;
    }

    protected void executeInternal(){
        List<ChainNode> currentNodes = getStartNodes();
        while(CollectionUtil.hasItems(currentNodes)){
            ChainNode currentNode = currentNodes.remove(0);

            Integer execCount = (Integer) currentNode.getMemory().get(CTX_EXEC_COUNT);
            if (execCount == null) execCount = 0;

            ChainCondition nodeCondition = currentNode.getCondition();
            if (nodeCondition != null && !nodeCondition.check(this, this.getMemory())) {
                continue;
            }

            Map<String, Object> executeResult = null;
            try{
                ChainContext.setNode(currentNode);
                notifyEvent(new OnNodeStartEvent(currentNode));
                if (this.getStatus() != ChainStatus.RUNNING) {
                    break;
                }
                executeResult = executeNode(currentNode);
            }finally {
                ChainContext.clearNode();
                currentNode.getMemory().put(CTX_EXEC_COUNT, execCount + 1);
                notifyEvent(new OnNodeFinishedEvent(currentNode, executeResult));
            }

            if(executeResult != null && !executeResult.isEmpty()){
                this.memory.putAll(executeResult);
            }

            if (this.getStatus() != ChainStatus.RUNNING) {
                break;
            }

            List<ChainEdge> outwardEdges = currentNode.getOutwardEdges();
            if (CollectionUtil.hasItems(outwardEdges)) {
                for (ChainEdge chainEdge : outwardEdges) {
                    ChainNode nextNode = getNodeById(chainEdge.getTarget());
                    if (nextNode == null) {
                        continue;
                    }
                    ChainCondition condition = chainEdge.getCondition();
                    if (condition == null) {
                        currentNodes.add(nextNode);
                    } else if (condition.check(this, this.getMemory())) {
                        currentNodes.add(nextNode);
                    }
                }
            }
        }
    }

    public void execute(Map<String, Object> variables) {
        runInLifeCycle(variables, this::executeInternal);
    }

    public <T> T executeForResult(String key, Object variable) throws ChainException {
        Map<String, Object> variables = new HashMap<>(1);
        variables.put(key, variable);
        this.execute(variables);

        if (this.status != ChainStatus.FINISHED_NORMAL) {
            throw new ChainException(this.message);
        }

        return (T) this.getMemory().get(Output.DEFAULT_VALUE_KEY);
    }

    public <T> T executeForResult(Object variable) throws ChainException {
        return executeForResult(Output.DEFAULT_VALUE_KEY, variable);
    }

    public void stopNormal(String message) {
        this.message = message;
        setStatus(ChainStatus.FINISHED_NORMAL);
    }

    public void stopError(String message) {
        this.message = message;
        setStatus(ChainStatus.FINISHED_ABNORMAL);
    }

    public void output(Agent agent, Object response) {
        notifyOutput(agent, response);
    }

    public String getMessage() {
        return message;
    }

    public List<ChainEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<ChainEdge> edges) {
        this.edges = edges;
    }

    public void addEdge(ChainEdge edge){
        if(this.edges == null){
            this.edges = new ArrayList<>();
        }
        this.edges.add(edge);

        boolean findSource = false, findTarget = false;

        for (ChainNode node : this.nodes) {
            if (node.getId().equals(edge.getSource())) {
                node.addOutwardEdge(edge);
                findSource = true;
            } else if (node.getId().equals(edge.getTarget())) {
                node.addInwardEdge(edge);
                findTarget = true;
            }
            if (findSource && findTarget) {
                break;
            }
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExecutorService getAsyncNodeExecutors() {
        return asyncNodeExecutors;
    }

    public void setAsyncNodeExecutors(ExecutorService asyncNodeExecutors) {
        this.asyncNodeExecutors = asyncNodeExecutors;
    }

    @Override
    public String toString() {
        return "Chain{" +
                "id='" + id + '\'' +
                ", memory=" + memory +
                ", eventListeners=" + eventListeners +
                ", outputListeners=" + outputListeners +
                ", nodes=" + nodes +
                ", lines=" + edges +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }


}
