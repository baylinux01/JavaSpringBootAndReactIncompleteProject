import React, { useEffect,useState } from 'react';
import {useParams,Link} from "react-router-dom";
import axios from "axios";
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import 'bootstrap/dist/css/bootstrap.min.css';
import _ from "lodash";
import { ListGroup, ListGroupItem } from 'react-bootstrap';

export default function Message({messages,setMessages,connectionsOfUser,setConnectionsOfUser,connectionRequests,setConnectionRequests,password,setPassword,user2,setUser2,user,setUser}) {
    const [newMessageContent,setNewMessageContent]=useState("");
    const [messageToBeQuoted,setMessageToBeQuoted]=useState({messageContent:""});
    const{user2Id}=useParams();
    const fetchUser=()=>{
      axios.defaults.baseURL="http://localhost:8080";
      return axios.get("/users/getoneuserbyid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:localStorage.getItem("id")}})
      .then((response)=>{setUser(response.data)});
    }
    const fetchUser2=()=>{
      axios.defaults.baseURL="http://localhost:8080";
      return axios.get("/users/getoneuserbyid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:user2Id}})
      .then((response)=>{setUser2(response.data)});
    }
    function getMessagesBetweenTwoUsers(){
        axios.defaults.baseURL="http://localhost:8080";
        
        axios.get("/messages/getmessagesbetweentwousers",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{user2Id:user2Id}})
        .then((response)=>{setMessages([...response.data])});
      }

      function handlenewmessage(e)
      {
          e.preventDefault();
          
        //axios kütüphanesi npm install axios kodu ile indirilebilir.
        //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
        //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
       
        axios.defaults.baseURL="http://localhost:8080";
        const qs=require('qs');
        axios.post("/messages/createmessage", 
          qs.stringify({messageReceiverId:user2Id,messageContent:newMessageContent,
            quotedMessageId:messageToBeQuoted.id
          })
        ,{
          auth: {
            username: localStorage.getItem("username"),
            password: localStorage.getItem("password")
          }
        });
        
        
        
        setNewMessageContent("");
        setMessageToBeQuoted({messageContent:""});
        getMessagesBetweenTwoUsers();
      
      }
      function deleteMessage(mId)
      {
          
          
        //axios kütüphanesi npm install axios kodu ile indirilebilir.
      
       axios.defaults.baseURL="http://localhost:8080";
       axios.delete("/messages/deletemessage",
        {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{messageId:mId}});
      
      
       
       
      }

    useEffect(() => {
        if(localStorage.getItem("id")!=null)
            {
              
                  fetchUser();
                
              fetchUser2();
              getMessagesBetweenTwoUsers();
            }

    }, [])
    
  
    return (
    <div style={{overflow:"scroll",border:"1px solid black", width:"900px",height:"auto",marginLeft:"50px",marginTop:"50px"}}>
        {messages.map(m => { 
            
               return(
                <div>


                {m.quotedMessage!=null?
                <div>
                <div>{m.quotedMessage.messageSender.username}: {m.quotedMessage.messageContent}</div>
                <div>{m.messageSender.username}:  {m.messageContent}</div>
                <div style={{display:'flex',columnGap:"10px"}}>
                <Button onClick={()=>{setMessageToBeQuoted({...m})}}>Quote</Button>
                {_.isEqual(m.messageSender,user)?
                    <div style={{display:'flex',columnGap:"10px"}}>
                    <Button variant="warning" onClick={()=>{}}>Edit</Button>
                    <Button variant="danger" onClick={()=>{deleteMessage(m.id)}}>Delete</Button>
                    </div>:
                    <div></div>
                }
                </div>
                <div>....</div>
                </div>
                :
                <div>
                <div></div>
                <div>{m.messageSender.username}:  {m.messageContent}</div>
                <div style={{display:'flex',columnGap:"10px"}}>
                <Button onClick={()=>{setMessageToBeQuoted({...m})}}>Quote</Button>
                {_.isEqual(m.messageSender,user)?
                    <div style={{display:'flex',columnGap:"10px"}}>
                    <Button variant="warning" onClick={()=>{}}>Edit</Button>
                    <Button variant="danger" onClick={()=>{deleteMessage(m.id)}}>Delete</Button>
                    </div>:
                    <div></div>
                }
                </div>
                <div>....</div>
                </div>}


                </div>
            );
               

           
})}
{messageToBeQuoted!=null && messageToBeQuoted.messageContent.length!=0? 
           <div>
           <ListGroup>
           <ListGroupItem>{messageToBeQuoted.messageContent}</ListGroupItem>
           </ListGroup>
           
           <Button variant="info" sync="true" onClick={()=>
             setMessageToBeQuoted({messageContent:""})}>Give up to quote message </Button>
           </div>
           :<div></div>}
            
             
                <Form sync="true" onSubmit={(e)=>handlenewmessage(e)}>
                {/* <Form action='http://localhost:8083/users/enteruser' method='post'> */}
        
                
                
              <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
                <div style={{width:"auto"}}>
                  New Message
                </div>
                <div>
                  <Form.Control type="text" placeholder="Message" name="content" autoComplete="off" value={newMessageContent} onChange={(e)=>setNewMessageContent(e.target.value)}/>
                </div>
              </Form.Group>
        
              
              
             
              
        
              <Form.Group as={Row} className="mb-3">
                <Col sm={{ span: 10, offset: 2 }}>
                   <Button type="submit" >Send New Message</Button> 
                </Col>
              </Form.Group>
            </Form>
</div>)}
