package msgpack

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
class Message {

    String body
    String note
    Date dateCreated
    Date dateUpdated
    User owner
    Long postalId
    Float price
    Integer readCount
    Boolean isPublic
    Map<String,String> props
    List<Comment> comments
    String transientProp
    Long unusedField

    static transients = ['transientProp', 'unusedField']
    static hasMany = [comments: Comment]
    static mapping = { 
        comments lazy: false
        comments cascade: 'all-delete-orphan'
        owner lazy: false
        owner cascade: 'all'
    }

    static constraints = {
        body blank: false
        note nullable: true
        dateUpdated nullable: true
        postalId nullable: true
    }
}
