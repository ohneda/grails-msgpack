package msgpack.beans

import org.msgpack.annotation.Ignore;
import org.msgpack.annotation.MessagePackBeans;
import org.msgpack.annotation.Optional;

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
@MessagePackBeans
class Comment {

    String title
    String body
    @Optional
    Long id
    @Optional
    Long version
    @Ignore
    Message message

}
