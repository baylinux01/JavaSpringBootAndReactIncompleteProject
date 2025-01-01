
import React, { useEffect,useState } from 'react';
import {useParams,Link} from "react-router-dom";
import axios from "axios";
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import 'bootstrap/dist/css/bootstrap.min.css';
import _ from "lodash";
import { Modal, ListGroup, ListGroupItem } from 'react-bootstrap';



export default function Group({password,setPassword,group,setGroup,user,setUser,bannedUsersOfUser,setBannedUsersOfUser}) {

  
const[newCommentContent,setNewCommentContent] =useState("");
const[commentToBeQuoted,setCommentToBeQuoted]=useState({content:""});
const[groupMembers,setGroupMembers]=useState([]);
const[groupComments,setGroupComments]=useState([]);
const[bannedUsersOfCommentOwner,setBannedUsersOfCommentOwner]=useState([]);
const [showPopUp,setShowPopUp]=useState(false);
const [showPopUp2,setShowPopUp2]=useState(false);
const [showPopUp3,setShowPopUp3]=useState(false);
const [user3Id,setUser3Id]=useState();
const [permissionsOfAUserForAGroup,setPermissionsOfAUserForAGroup]=useState("");
const [permissionsOfCurrentUserForAGroup,setPermissionsOfCurrentUserForAGroup]=useState("");
const [commentContentToBeEdited,setCommentContentToBeEdited]=useState("");

const{groupId}=useParams();

function fetchGroup(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/groups/getonegroupbyid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{groupId:groupId}}).then((response)=>{setGroup({...response.data})});
  }
function fetchComments(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/comments/getcommentsofagroup",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{groupId:groupId}}).then((response)=>{setGroupComments([...response.data])});
  }
  function fetchMembers(){
    axios.defaults.baseURL="http://localhost:8080";
    return axios.get("/groups/getonegroupmembersbygroupid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{groupId:groupId}}).then((response)=>{setGroupMembers([...response.data])});
  }

  function fetchUser(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/users/getoneuserbyid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:localStorage.getItem("id")}})
    .then((response)=>{setUser({...response.data})});
    console.log(user)
  }

  function fetchBannedUsersOfUser(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/users/getbannedusersofcurrentuser",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{}})
    .then((response)=>{setBannedUsersOfUser([...response.data])});
    
  }

  function fetchBannedUsersOfCommentOwner(id){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/users/getbannedusersofauser",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:id}})
    .then((response)=>{setBannedUsersOfCommentOwner([...response.data])});
    
  }
  function fetchPermissionsOfAUserForAGroup(id){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/usergrouppermissions/getpermissionsofauserforagroup",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:id,groupId:groupId}})
    .then((response)=>{setPermissionsOfAUserForAGroup(response.data)});
    
  }
  function fetchPermissionsOfCurrentUserForAGroup(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/usergrouppermissions/getpermissionsofauserforagroup",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:localStorage.getItem("id"),groupId:groupId}})
    .then((response)=>{setPermissionsOfCurrentUserForAGroup(response.data)});
    console.log(permissionsOfCurrentUserForAGroup);
    
  }
  function deleteComment(commentId)
  {
    axios.defaults.baseURL="http://localhost:8080";
    axios.delete("/comments/deletecomment",
      {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{commentId:commentId}});
    fetchComments();
    // axios.defaults.baseURL="http://localhost:8080";
    // const qs=require('qs');
    // axios.delete("/comments/deletecomment", 
    //   qs.stringify({commentId:commentId
    //   })
    // ,{
    //   auth: {
    //     username: user.username,
    //     password: password
    //   }
    // });
  }
  function editCommentContent(mId,newContent)
  {
      
      
    //axios kütüphanesi npm install axios kodu ile indirilebilir.
   const params=new URLSearchParams();
   params.append("commentId",mId);
   params.append("newcontent",newContent);
   axios.defaults.baseURL="http://localhost:8080";
   axios.put("/comments/updatecomment",params,{auth:{username:localStorage.getItem("username"),password:localStorage.getItem("password")}});
  // axios.put("/messages/editmessagecontent", null, {
  //   headers: {
  //     'Authorization': 'Basic ' + btoa(localStorage.getItem("username") + ':' + localStorage.getItem("password"))
  //   },
  //   params: params
  // });
  
  fetchComments();
  setShowPopUp(false);
  window.history.go(0);
   
  }

  function addSendMessagePermission(id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
          //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
          //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
         const params=new URLSearchParams();
         params.append("userId",id);
         params.append("groupId",groupId);

         axios.defaults.baseURL="http://localhost:8080";
    const qs=require('qs');
    axios.put("/usergrouppermissions/addsendmessagepermission", 
      params,{auth:{username:localStorage.getItem("username"),password:localStorage.getItem("password")}});
    }
    function removeSendMessagePermission(id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
          //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
          //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
         const params=new URLSearchParams();
         params.append("userId",id);
         params.append("groupId",groupId);

         axios.defaults.baseURL="http://localhost:8080";
    const qs=require('qs');
    axios.put("/usergrouppermissions/removesendmessagepermission", 
      params,{auth:{username:localStorage.getItem("username"),password:localStorage.getItem("password")}});
    }
    useEffect(()=> {

     
    
         fetchUser();
        fetchGroup();
        fetchComments();
        fetchMembers();   
        fetchBannedUsersOfUser();
        fetchPermissionsOfCurrentUserForAGroup();
        
        
     },[]);

     

     const disdiv={
      display:"flex",
      justifyContent:"center",
      alignItems:"center",
      flexDirection:"column",
      rowGap:"20px"
     }
     const baslik={
      width:"300px",
      textAlign:"center",
      border:"1px solid black",
      fontSize:"2rem"
  }

  function handlenewcomment(e)
    {
        e.preventDefault();
        
      //axios kütüphanesi npm install axios kodu ile indirilebilir.
      //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
      //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
     
      axios.defaults.baseURL="http://localhost:8080";
      const qs=require('qs');
      axios.post("/comments/createcomment", 
        qs.stringify({content:newCommentContent,
          commentIdToBeQuoted:commentToBeQuoted.id, groupId:group.id
        })
      ,{
        auth: {
          username: localStorage.getItem("username"),
          password: localStorage.getItem("password")
        }
      });
      
      
      fetchComments();
      fetchMembers();
      fetchGroup();
      fetchUser();
      fetchBannedUsersOfUser();
      setNewCommentContent("");
      setCommentToBeQuoted({content:""});
      window.history.go(0);
    
    }
    
    
    
      
    
    
    



  return (
    
        <div style={disdiv}>
          <Button variant='warning' onClick={()=>setShowPopUp2(true)}>See Members</Button>
        <div style={baslik}>{group.name}</div>
        {groupComments.map(comment=>
        <div>
        <ListGroup>
        {comment.quotedComment!=null && comment.quotedComment.content!=null &&
         comment.quotedComment.content.length>0? 
          <div>
        <ListGroup.Item style={{backGroundColor:"yellow"}}>Quoted Comment Owner: <Link to={"/users/user/"+comment.quotedComment.owner.id}>
          {comment.quotedComment.owner.name}</Link></ListGroup.Item>
        <ListGroup.Item>Quoted Comment Content:{comment.quotedComment.content}</ListGroup.Item>
        </div>:
      <div>
      <div></div>
      <div></div>
      </div>}
      
      
      
        <ListGroup.Item>Comment Owner: <Link to={"/users/user/"+comment.owner.id}>{comment.owner.name} {comment.owner.surname}</Link></ListGroup.Item>
        <ListGroup.Item>Comment Content: {comment.content}</ListGroup.Item>
        {_.find(group.members,user)?
          <Button variant="info" sync="true" onClick={()=>!_.find(bannedUsersOfUser,comment.owner.id)
            && !_.find(bannedUsersOfCommentOwner,user)?
            setCommentToBeQuoted({...comment}):
        setCommentToBeQuoted(null)}>Quote</Button>
          :<div></div>}
    {_.isEqual(comment.owner,user)?
    <div style={{display:"flex",columnGap:"10px", justifyContent:"end"}}>
      <Button variant="warning" sync="true" onClick={()=>{setShowPopUp(true)}}>Edit</Button>
          <Button variant="danger" sync="true" onClick={()=>deleteComment(comment.id)}>Delete</Button>
          <Modal show={showPopUp} onHide={()=>setShowPopUp(false)}>
    <Modal.Header>
      <Modal.Title>Edit Message</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <input type='text' id='newcontent' onChange={(e)=>setCommentContentToBeEdited(e.target.value)}></input>
      
    </Modal.Body>
    <Modal.Footer>
    <Button variant='primary' onClick={()=>{editCommentContent(comment.id,commentContentToBeEdited)}}>Edit Comment</Button>
    <Button variant='primary' onClick={()=>{setShowPopUp(false)}}>Close Pop-Up</Button>
    </Modal.Footer>
    </Modal>
          </div> 
          :<div></div>}
      </ListGroup>
      </div>
        )}
        
        
        {commentToBeQuoted!=null && commentToBeQuoted.content.length!=0? 
       <div>
       <ListGroup>
       <ListGroupItem>{commentToBeQuoted.content}</ListGroupItem>
       </ListGroup>
       
       <Button variant="info" sync="true" onClick={()=>
         setCommentToBeQuoted({content:""})}>Give up to quote comment </Button>
       </div>
       :<div></div>}
        
         {_.find(group.members,user)&&permissionsOfCurrentUserForAGroup.includes("SENDMESSAGE")?
            <Form sync="true" onSubmit={(e)=>handlenewcomment(e)}>
            {/* <Form action='http://localhost:8083/users/enteruser' method='post'> */}
    
            
            
          <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
            <div style={{width:"auto"}}>
              New Comment
            </div>
            <div>
              <Form.Control type="text" placeholder="Comment" name="content" autoComplete="off" value={newCommentContent} onChange={(e)=>setNewCommentContent(e.target.value)}/>
            </div>
          </Form.Group>
    
          
          
         
          
    
          <Form.Group as={Row} className="mb-3">
            <Col sm={{ span: 10, offset: 2 }}>
               <Button type="submit" >Make New Comment</Button> 
            </Col>
          </Form.Group>
        </Form>
            :<div>The owner or admins of this group have restricted your ability to type comments</div>}
        <Modal show={showPopUp2} onHide={()=>setShowPopUp2(false)}>
    <Modal.Header>
      <Modal.Title>Members</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      {groupMembers.map(member=>
      <div style={{display:"flex",columnGap:"10px"}}>
        <div>{member.name} {member.surname}</div>
        {_.isEqual(user,group.owner)?
        <Button variant='warning' onClick={()=>{setUser3Id(member.id);fetchPermissionsOfAUserForAGroup(member.id);setShowPopUp3(true)}}>Change Permissions</Button>
      :<div></div>
      }
      </div>

)}
      
    </Modal.Body>
    <Modal.Footer>
    
    <Button variant='primary' onClick={()=>{setShowPopUp2(false)}}>Close Pop-Up</Button>
    </Modal.Footer>
    </Modal>

    <Modal show={showPopUp3} onHide={()=>setShowPopUp3(false)}>
    <Modal.Header>
      <Modal.Title>Permissions</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <div>
      {permissionsOfAUserForAGroup.includes("SENDMESSAGE")?
      <label>
        <input type='checkbox' defaultChecked={true} onChange={()=>{removeSendMessagePermission(user3Id);fetchPermissionsOfAUserForAGroup(user3Id)}}>
        </input>
        SEND MESSAGE
      </label>
      :
      <label>
        <input type='checkbox' defaultChecked={false} onChange={()=>{addSendMessagePermission(user3Id);fetchPermissionsOfAUserForAGroup(user3Id)}}>
        </input>
        SEND MESSAGE
      </label>
    }
    <span style={{marginLeft:"10px"}}></span>
    {permissionsOfAUserForAGroup.includes("SENDMEDIA")?
      <label>
        <input type='checkbox' defaultChecked={true}>
        </input>
        SEND MEDIA
      </label>
      :
      <label>
        <input type='checkbox' defaultChecked={false}>
        </input>
        SEND MEDIA
      </label>
    }
      </div>

      
    </Modal.Body>
    <Modal.Footer>
    
    <Button variant='primary' onClick={()=>{fetchPermissionsOfAUserForAGroup(user3Id);setShowPopUp3(false)}}>Close Pop-Up</Button>
    </Modal.Footer>
    </Modal>
    
        </div>
    
    
  )}