package msgpack

import java.util.List;

class PropertyTestDomain {
    
    static singleBelongsTo = Message
    static listBelongsTo = [Message, Role]
    static mapBelongsTo = [message: Message, role:Role, comment: Comment]
    
    List<Message> listForGeneric
    List justList
    Map<String, Message> mapForGeneric1
    Map<Role, Message> mapForGeneric2
    Map justMap
    Set<Comment> setForGeneric
    Set justSet

    Role role
    User user
    static belongsTo = [Role, User]

}
