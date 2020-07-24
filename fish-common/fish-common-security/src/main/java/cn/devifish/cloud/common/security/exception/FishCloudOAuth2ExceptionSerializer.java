package cn.devifish.cloud.common.security.exception;

import cn.devifish.cloud.common.core.RestfulEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * FishCloudOAuth2ExceptionSerializer
 * 框架OAuth2异常JSON序列化工具
 *
 * @author Devifish
 * @date 2020/7/24 15:13
 */
public class FishCloudOAuth2ExceptionSerializer extends StdSerializer<FishCloudOAuth2Exception> {

    protected FishCloudOAuth2ExceptionSerializer() {
        super(FishCloudOAuth2Exception.class);
    }

    @Override
    public void serialize(FishCloudOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        var code = value.getHttpErrorCode();
        var message = value.getMessage();
        var entity = new RestfulEntity<Void>(null, code, message);
        gen.writeObject(entity);
    }
}
