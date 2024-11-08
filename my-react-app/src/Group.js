
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



export default function Group({password,setPassword,group,setGroup,user,setUser,bannedUsersOfUser,setBannedUsersOfUser}) {

  
const[newCommentContent,setNewCommentContent] =useState("");
const[commentToBeQuoted,setCommentToBeQuoted]=useState({content:""});
const[groupMembers,setGroupMembers]=useState([]);
const[groupComments,setGroupComments]=useState([]);
const[bannedUsersOfCommentOwner,setBannedUsersOfCommentOwner]=useState([]);

const{groupId}=useParams();

function fetchGroup(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/groups/getonegroupbyid",{auth: {username: user.username,password: password},params:{groupId:groupId}}).then((response)=>{setGroup({...response.data})});
  }
function fetchComments(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/comments/getcommentsofagroup",{auth: {username: user.username,password: password},params:{groupId:groupId}}).then((response)=>{setGroupComments([...response.data])});
  }
  function fetchMembers(){
    axios.defaults.baseURL="http://localhost:8080";
    return axios.get("/groups/getonegroupmembersbygroupid",{auth: {username: user.username,password: password},params:{groupId:groupId}}).then((response)=>{setGroupMembers([...response.data])});
  }

  function fetchUser(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/users/getoneuserbyid",{auth: {username: user.username,password: password},params:{userId:user.id}})
    .then((response)=>{setUser({...response.data})});
    console.log(user)
  }

  function fetchBannedUsersOfUser(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/users/getbannedusersofcurrentuser",{auth: {username: user.username,password: password},params:{}})
    .then((response)=>{setBannedUsersOfUser([...response.data])});
    
  }

  function fetchBannedUsersOfCommentOwner(id){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/users/getbannedusersofauser",{auth: {username: user.username,password: password},params:{userId:id}})
    .then((response)=>{setBannedUsersOfCommentOwner([...response.data])});
    
  }

  function deleteComment(commentId)
  {
    axios.defaults.baseURL="http://localhost:8080";
    axios.delete("/comments/deletecomment",
      {auth: {username: user.username,password: password},params:{commentId:commentId}});
    
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


    useEffect(()=> {

     
    

        fetchGroup();
        fetchComments();
        fetchMembers(); 
        fetchUser();  
        fetchBannedUsersOfUser();
        
     });

     

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
          username: user.username,
          password: password
        }
      });
      
      
      fetchComments();
      fetchMembers();
      fetchGroup();
      fetchUser();
      fetchBannedUsersOfUser();
      setNewCommentContent("");
      setCommentToBeQuoted({content:""});
    
    }
    
    
    
      
    
    
    



  return (
    
        <div style={disdiv}>
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
    {_.isEqual(comment.owner,user)|| user.roles.includes("ADMIN")?
          <Button variant="danger" sync="true" onClick={()=>deleteComment(comment.id)}>Delete</Button>
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
        
         {_.find(group.members,user)?
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
            :<div></div>}
        
        </div>
    
    
  )}