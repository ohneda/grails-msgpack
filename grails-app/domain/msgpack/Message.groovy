package msgpack

import org.msgpack.annotation.*;

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
@MessagePackBeans
class Message {

    @Optional
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

    static hasMany = [comments: Comment]
    static mapping = { comments lazy: false; owner lazy: false}

    static constraints = {
        body blank: false
        note nullable: true
        dateUpdated nullable: true
        postalId nullable: true
    }
}
