package msgpack

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
class Role {

    String name
    String title

    def static belongsTo = [user: User]
    static mapping = { 
        user lazy: false
    }

    static constraints = {
        user nullable: false 
    }
}
