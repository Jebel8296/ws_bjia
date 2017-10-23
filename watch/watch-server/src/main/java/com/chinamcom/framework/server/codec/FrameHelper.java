package com.chinamcom.framework.server.codec;

import org.apache.log4j.Logger;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.WriteStream;

/**
 * Created by Administrator on 2016/7/29.
 */
public class FrameHelper {
	private static Logger LOG = Logger.getLogger(FrameHelper.class);
    private FrameHelper() {
    }

    public static void sendFrame(String to, String command, Integer msgId, JsonObject headers, JsonObject body, Integer ack, WriteStream<Buffer> stream) {
        JsonObject payload = (new JsonObject()).put("cmd", command);

        if(headers != null) {
            payload.put("header", headers);
        }
        if(msgId != null){
        	payload.put("sn", msgId);
        }   
        if(body != null) {
            payload.put("body", body);
        }
        if(ack != null) {
            payload.put("ack", ack);
        }
        if(!payload.toString().contains("\"cmd\":\"hbt\"")){
        	LOG.info("send [" + to +"] : " + payload.toString());
        }
        LOG.debug("send [" + to +"] : " + payload.toString());
        byte[] data = payload.encode().getBytes();
        stream.write(Buffer.buffer().appendInt(data.length).appendBytes(data));
    }
}
