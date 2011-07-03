package msgpack

import org.msgpack.annotation.MessagePackBeans;
import org.msgpack.annotation.Optional;

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
@MessagePackBeans
class User {

    String name
    String title
    @Optional
    Long id
    @Optional
    Long version
    // MessagePack for Java have not supported bidirectional the relationships yet.
    //static belongsTo = [message: Message]
    static constraints = {
        title nullable: true
        name blank: false
    }
}
