package org.grails.msgpack

import org.msgpack.MessagePack

import grails.plugin.spock.IntegrationSpec;
import msgpack.Message
import msgpack.Role
import msgpack.User
import msgpack.Comment

class GroovyBeansRegisterSpec extends IntegrationSpec{

    def 'simple pack and unpack'(){

        given:
        GroovyBeansRegister register = GroovyBeansRegister.instance

        when: 'I regist a groovy bean object with GroovyBeanRegister'
        register.register(Message)

        def user = new User(name: "name", title: "title")
        def role = new Role(name:'system', title:'manager')
        role.save()
        user.role = role
        role.user =user

        def message = new Message(body: "body",
                note: "notes",
                dateCreated: new Date(),
                dateUpdated: new Date(),
                postalId: 1234567,
                price: 123.55,
                readCount: 10,
                isPublic: true,
                owner: user,
                props: ['p1':'v1', 'p2':'v2'],
                comments: [new Comment(title: "title:1", body: "body:1"), new Comment(title: "title:2", body: "body:2")]
                )
        message.save()
        byte[] packed = MessagePack.pack(message)

        then: 'MessagePack can pack Groovy Beans Object'
        packed != null

        when: 'I unpack the packed bytes to the other beans which has same fields structures.'
        msgpack.beans.Message unpack = MessagePack.unpack(packed, msgpack.beans.Message.class)

        then: 'the groovy bean object can be unserialized as it was before'
        unpack.class == msgpack.beans.Message
        unpack.id == message.id
        unpack.version == message.version
        unpack.body == message.body
        unpack.note == message.note
        unpack.dateCreated == message.dateCreated
        unpack.dateUpdated == message.dateUpdated
        unpack.postalId == message.postalId
        unpack.price == message.price
        unpack.readCount == message.readCount
        unpack.isPublic == message.isPublic
        unpack.owner.id == message.owner.id
        unpack.owner.version == message.owner.version
        unpack.owner.title == message.owner.title
        unpack.owner.name == message.owner.name
        unpack.owner.role.title == role.title
        unpack.owner.role.name == role.name
        unpack.comments[0].id == message.comments[0].id
        unpack.comments[0].version == message.comments[0].version
        unpack.comments[0].title == message.comments[0].title
        unpack.comments[0].body == message.comments[0].body
        unpack.comments[1].id == message.comments[1].id
        unpack.comments[1].version == message.comments[1].version
        unpack.comments[1].title == message.comments[1].title
        unpack.comments[1].body == message.comments[1].body

    }

    def 'allow some fields to be null with Optional annotation'(){

                given:
                GroovyBeansRegister register = GroovyBeansRegister.instance

                when: 'I regist a groovy bean object with GroovyBeanRegister'
                register.register(msgpack.Message)
                and: 'some filed, in this case id and version, is null'

                def user = new User(name: "name", title: "title")
                def message = new Message(body: "body",
                        note: "notes",
                        dateCreated: new Date(),
                        dateUpdated: new Date(),
                        postalId: 1234567,
                        price: 123.55,
                        readCount: 10,
                        isPublic: true,
                        owner: user,
                        props: ['p1':'v1', 'p2':'v2'],
                        comments: [new Comment(title: "title:1", body: "body:1"), new Comment(title: "title:2", body: "body:2")]
                        )

                byte[] packed = MessagePack.pack(message)

                then: 'MessagePack can pack Groovy Beans Object'
                packed != null

                when: 'I unpack the packed bytes'
                msgpack.beans.Message unpack = MessagePack.unpack(packed, msgpack.beans.Message.class)

                then: 'the groovy bean object can be unserialized as before'
                unpack.class == msgpack.beans.Message
                unpack.body == message.body
                unpack.note == message.note
                unpack.dateCreated == message.dateCreated
                unpack.dateUpdated == message.dateUpdated
                unpack.postalId == message.postalId
                unpack.price == message.price
                unpack.readCount == message.readCount
                unpack.isPublic == message.isPublic
                unpack.owner.title == message.owner.title
                unpack.owner.name == message.owner.name
                unpack.comments[0].title == message.comments[0].title
                unpack.comments[0].body == message.comments[0].body
                unpack.comments[1].title == message.comments[1].title
                unpack.comments[1].body == message.comments[1].body

                and: 'fields which is annotated with Optional are nullable'
                unpack.id == null
                unpack.version == null
                unpack.owner.id == null
                unpack.owner.version == null
                unpack.comments[0].id == null
                unpack.comments[0].version == null
                unpack.comments[1].id == null
                unpack.comments[1].version == null

            }
}
