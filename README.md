# LLMAgents
LLMAgents致力于搭建Android平台LLM云/端协同大模型架构

项目架构模块如下
+ LLM : 封装各厂商LLM大模型相关服务，包括云/端大模型
+ Prompt 模块: 提供架构中所有Prompt搭建服务
+ LLMChain 模块: 提供链式结构请求服务
+ LLMAgent 模块: 提供Agent引擎
+ FunctionTool : 封装不同类似的Tool工具
+ Message : 消息体模块，提供包括System、AI、Human在内的消息体类型
+ Memory : 记忆模块
