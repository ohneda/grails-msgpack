package msgpack

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
class Comment {

    String title
    String body

    // MessagePack for Java have not supported bidirectional the relationships yet.
    //static belongsTo = [message: Message]
    static constraints = {
        body blank: false
        title nullable: true
    }
}
