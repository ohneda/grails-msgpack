package msgpack.beans

import org.msgpack.annotation.*;

/**
 * This domain class is not for production use, and doesn't be included in plugin package.
 *
 * @author ohneda
 *
 */
@MessagePackBeans
class Message {

    String body
    @Optional
    String note
    Date dateCreated
    @Optional
    Date dateUpdated
    User owner
    @Optional
    Long postalId
    Float price
    Integer readCount
    Boolean isPublic
    Map<String,String> props
    List<Comment> comments
    @Optional
    Long id
    @Optional
    Long version

}
