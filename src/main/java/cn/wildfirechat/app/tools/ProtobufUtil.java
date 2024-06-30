package cn.wildfirechat.app.tools;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;

/**
 * @Description: protobuf工具类
 * @author: Dragon
 * @date: 2024/6/3 23:32
 */
public class ProtobufUtil {

    /**
     * protobuf转json字符串
     * @param messageOrBuilder
     * @return
     * @throws IOException
     */
    public static String toJson(MessageOrBuilder messageOrBuilder) throws IOException {
        return JsonFormat.printer().print(messageOrBuilder);
    }
}
