
package com.llm.agents.core.prompt;

public interface PromptFormat {

    Object toMessagesJsonObject(Prompt<?> prompt);

    Object toFunctionsJsonObject(Prompt<?> prompt);
}
