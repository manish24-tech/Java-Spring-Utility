package com.practice.processor;

import org.springframework.messaging.Message;

/**
 * Custom Bean that registerd in IOC container
 * it's purpose to print message of file with name and content 
 * @author binal
 *
 */
public class FileProcessor {

	private static final String HEADER_FILE_NAME = "file_name";
    private static final String MSG = "%s received. Content: %s";

    public void process(Message<String> msg) {
        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
        String content = msg.getPayload();

        System.out.println(String.format(MSG, fileName, content));
    }
}

