package msgpack

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
class User {

    String name
    String title

    static belongsTo = [message: Message]
    static constraints = {
        title nullable: true
        name blank: false
    }
}
