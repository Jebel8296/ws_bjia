package com.chinamcom.framework.server.codec;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import org.apache.log4j.Logger;

public class FrameParser implements Handler<Buffer> {
    private static Logger LOG = Logger.getLogger(FrameParser.class);
    private Buffer _buffer;
    private int _offset;
    private final Handler<AsyncResult<String>> client;

    public FrameParser(Handler<AsyncResult<String>> client) {
        this.client = client;
    }

    public void handle(Buffer buffer) {
        LOG.debug("recv buff size:" + buffer.getBytes().length);
        LOG.debug("recv buff:" + new String(buffer.getBytes()));
        this.append(buffer);

        while (true) {
            int offset = this._offset;
            int remainingBytes = this.bytesRemaining();
            if (remainingBytes < 4) {
                break;
            }

            int length = this._buffer.getInt(this._offset);
            this._offset += 4;
            if (remainingBytes - 4 < length) {
                this._offset = offset;
                break;
            }

            try {
                String data = this._buffer.getString(this._offset, this._offset + length);
                if(!data.contains("\"cmd\":\"hbt\"")){
                	LOG.info("recv data:" + data);
                }
                LOG.debug("recv data:" + data);
                this.client.handle(Future.succeededFuture(data));
            } catch (DecodeException var6) {
                this.client.handle(Future.failedFuture(var6));
            }

            this._offset += length;
        }

    }

    private void append(Buffer newBuffer) {
        if (newBuffer != null) {
            if (this._buffer == null) {
                this._buffer = newBuffer;
            } else if (this._offset >= this._buffer.length()) {
                this._buffer = newBuffer;
                this._offset = 0;
            } else {
                if (this._offset > 0) {
                    this._buffer = this._buffer.getBuffer(this._offset, this._buffer.length());
                }

                this._buffer.appendBuffer(newBuffer);
                this._offset = 0;
            }
        }
    }

    private int bytesRemaining() {
        return this._buffer.length() - this._offset < 0 ? 0 : this._buffer.length() - this._offset;
    }
}
