package msgpack

/**
* This service class is not for production use, and doesn't be included in plugin package.
*
* @author ohneda
*
*/
class MessageService {

    static expose = ['msgpack']
    static exclude=['fullMessageInstance']
    static transactional = true

    Message get(Integer id){
        Message.get(id)
    }

    Integer create(Message message){
        message.owner.save()
        message.save()
        message.id
    }

    Message update(Message message){

        assert message.id
        def target = Message.get(message.id)

        def owner = target.owner
        def comments = target.comments
        owner.properties = message.owner.properties
        target.properties = message.properties

        // update each children element since you can't use the bidirectional relationship for now
        target.owner = owner
        target.owner.properties = message.owner.properties
        target.owner.save()

        target.comments = comments
        target.comments.eachWithIndex{ it, index ->
            it.properties = message.comments.get(index).properties
            it.save()
        }

        target.save()

        target

    }

    void delete(Integer id){
        Message.delete(id)
    }

    // MessagePack RPC for Java have not supported method overloading
    // see org.msgpack.rpc.reflect.MethodSelector, org.msgpack.rpc.dispatcher.MethodDispatcher
    /**
    Integer create(String body, String note){
        println "create(String ${body}, String ${note})"
        def message = fullMessageInstance();
        message.body = body
        message.note = note
        message.save()
        message.id
    }
    **/
    List<Message> list(){
        Message.list()
    }

    private fullMessageInstance(){
        def owner = new User( name: 'ohneda', title: 'manager' )
        def comment = new Comment( title: 'title', body: 'this is test' )
        def message = new Message(body: 'body', note: 'note', dateCreated: new Date(), dateUpdated: new Date(), postalId: 1234556666, price: 123.55, readCount: 10, isPublic: true, props: ['p1':'v1', 'p2':'v2'])
        message.addToComments( comment )
        message
    }

}
