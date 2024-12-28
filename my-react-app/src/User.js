
import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import _ from "lodash";
import { Table,Button, ListGroup, ListGroupItem } from 'react-bootstrap';


export default function User({connectionsOfUser,setConnectionsOfUser,connectionRequests,setConnectionRequests,password,setPassword,user2,setUser2,user,setUser}) {
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

    function getAllConnectionRequests(){
      axios.defaults.baseURL="http://localhost:8080";
      
      axios.get("/connectionrequests/getallconnectionrequests",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")}})
      .then((response)=>{setConnectionRequests([...response.data])});
    }
    function getAllConnectionsOfCurrentUser(){
      axios.defaults.baseURL="http://localhost:8080";
      
      axios.get("/users/getconnectionsofauser",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:localStorage.getItem("id")}})
      .then((response)=>{setConnectionsOfUser([...response.data])});
    }

    function sendConnectionRequest(user2Id)
        {
         
        const params=new URLSearchParams();
        params.append("connectionRequestReceiverId",user2Id);

         axios.defaults.baseURL="http://localhost:8080";
      //const qs=require('qs');
      axios.post("/connectionrequests/createconnectionrequest", 
        params
      ,{
        auth: {
          username: localStorage.getItem("username"),
          password: localStorage.getItem("password")
        }
      });

          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
          conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==user2.id);
            conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==user2.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];
         
        }
        function acceptConnectionRequest(user2Id)
        {
         
        const params=new URLSearchParams();
        params.append("userToBeAcceptedId",user2Id);

         axios.defaults.baseURL="http://localhost:8080";
      //const qs=require('qs');
      axios.post("/users/acceptconnection", 
        params
      ,{
        auth: {
          username: localStorage.getItem("username"),
          password: localStorage.getItem("password")
        }
      });

          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
          conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==user2.id);
            conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==user2.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];
         
        }
      
        function deleteConnection(Id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
        
         axios.defaults.baseURL="http://localhost:8080";
         axios.delete("/users/deleteconnection",
          {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userToBeDeletedId:Id}});
        
        
          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
          conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==user2.id);
            conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==user2.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];
         
        }
        function refuseConnectionRequest(Id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
        
         axios.defaults.baseURL="http://localhost:8080";
         axios.delete("/connectionrequests/refuseconnectionrequest",
          {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{connectionRequestSenderId:Id}});
        
        
          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
          conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==user2.id);
            conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==user2.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];
         
        }
        function cancelConnectionRequest(Id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
        
         axios.defaults.baseURL="http://localhost:8080";
         axios.delete("/connectionrequests/cancelconnectionrequest",
          {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{connectionRequestReceiverId:Id}});
        
        
          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
          conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==user2.id);
            conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==user2.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];
         
        }
        let conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==user2.id);
             let conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==user2.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];

   useEffect(()=> {
    //fetchGroup();
    //fetchComments();
    //fetchMembers();
    
    if(localStorage.getItem("id")!=null)
      {
        
            fetchUser();
          
        fetchUser2();
            getAllConnectionRequests();
            getAllConnectionsOfCurrentUser();
            conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==user2.id);
             conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==user2.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];
            console.log("hello world");
      }
    // fetch("/groups/getonegroupbyid?groupId="+groupId).then(response=>{return response.json()})
    // .then(data=>{setGroup(data)})
 },[])
  return (
    <div>
    {Object.keys(user).length!=0&&Object.keys(user2).length!==0&&_.find(connectionsOfUser,user2)?
    <div>
    <img style={{position:"absolute",height:"100px",width:"100px",
      top:"50px",left:"0px"
    }} src={`data:image/*;base64,${user2.userImage}`}/>
    <div style={{position:"absolute",
    top:"50px",left:"120px"
  }} >{user2.name} {user2.surname}</div>
    <Link to={"/message/"+user2.id}><Button style={{position:"absolute",top:"100px",left:"120px"}} variant="primary" sync="true" >Message</Button></Link>
    <Button style={{position:"absolute",top:"150px",left:"120px"}} variant="danger" sync="true" onClick={()=>deleteConnection(user2.id)}>Disconnect</Button>
    </div>
    :Object.keys(user).length!=0&&Object.keys(user2).length!==0&&!_.find(connectionsOfUser,user2)&&_.isEqual(conreq1.connectionRequestSender,user)?
      <div>
        <img style={{position:"absolute",height:"100px",width:"100px",
      top:"50px",left:"0px"
    }} src={`data:image/*;base64,${user2.userImage}`}/>
    <div style={{position:"absolute",
    top:"50px",left:"120px"
  }} >{user2.name} {user2.surname}</div>
        <Button style={{position:"absolute",top:"100px",left:"120px"}} variant="danger" sync="true" onClick={()=>cancelConnectionRequest(user2.id)}>Cancel Connection Request</Button>
      </div>
    :Object.keys(user).length!=0&&Object.keys(user2).length!==0&&!_.find(connectionsOfUser,user2)&&_.isEqual(conreq2.connectionRequestReceiver,user)?
    <div>
      <img style={{position:"absolute",height:"100px",width:"100px",
      top:"50px",left:"0px"
    }} src={`data:image/*;base64,${user2.userImage}`}/>
    <div style={{position:"absolute",
    top:"50px",left:"120px"
  }} >{user2.name} {user2.surname}</div>
   <Button style={{position:"absolute",top:"100px",left:"120px"}} variant="success" sync="true" onClick={()=>acceptConnectionRequest(user2.id)}>Accept Connection Request</Button>
   <Button style={{position:"absolute",top:"150px",left:"120px"}} variant="danger" sync="true" onClick={()=>refuseConnectionRequest(user2.id)}>Refuse Connection Request</Button>
    </div>
   
    :Object.keys(user).length!=0&&Object.keys(user2).length!=0&&!_.find(user.connections,user2)?
    <div>
      <img style={{position:"absolute",height:"100px",width:"100px",
      top:"50px",left:"0px"
    }} src={`data:image/*;base64,${user2.userImage}`}/>
    <div style={{position:"absolute",
    top:"50px",left:"120px"
  }} >{user2.name} {user2.surname}</div>
  <Button style={{position:"absolute",top:"100px",left:"120px"}} variant="warning" sync="true" onClick={()=>sendConnectionRequest(user2.id)}>Connect</Button>
    </div>
    :
    <div>In order to see user profile please log in</div>
  }
  
  </div>
)}
