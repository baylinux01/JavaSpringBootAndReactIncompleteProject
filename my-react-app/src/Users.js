import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import _ from "lodash";
import { Table,Button, ListGroup, ListGroupItem } from 'react-bootstrap';

export default function Users({ connectionsOfUser,setConnectionsOfUser, connectionRequests,setConnectionRequests,password,setPassword,group,setgroup,groups,setGroups,signupformsubmitresult,setSignupformsubmitresult,
    users,setUsers,user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}) {
        

        function fetchUser(){
            axios.defaults.baseURL="http://localhost:8080";
            
            axios.get("/users/getoneuserbyid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:localStorage.getItem("id")}})
            .then((response)=>{setUser({...response.data})});
          }
      function fetchUsers(){
        axios.defaults.baseURL="http://localhost:8080";
        
        axios.get("/users/getallusers")//,{auth: {username: user.username,password: password}})
        .then((response)=>{setUsers([...response.data])});
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
     useEffect(() => {
        if(localStorage.getItem("id")!=null)
            {
              fetchUser();
            }
       fetchUsers();
       getAllConnectionRequests();
       getAllConnectionsOfCurrentUser();
     
       
     }, [])

     
     
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
         
        }
      
        function deleteConnection(Id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
        
         axios.defaults.baseURL="http://localhost:8080";
         axios.delete("/users/deleteconnection",
          {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userToBeDeletedId:Id}});
        
        
          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
         
        }
        function refuseConnectionRequest(Id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
        
         axios.defaults.baseURL="http://localhost:8080";
         axios.delete("/connectionrequests/refuseconnectionrequest",
          {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{connectionRequestSenderId:Id}});
        
        
          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
         
        }
        function cancelConnectionRequest(Id)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
        
         axios.defaults.baseURL="http://localhost:8080";
         axios.delete("/connectionrequests/cancelconnectionrequest",
          {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{connectionRequestReceiverId:Id}});
        
        
          getAllConnectionRequests();
          getAllConnectionsOfCurrentUser();
         
        }
        let conreq1=null;
        let conreq2=null;
  return (
    <ListGroup style={{marginTop:"100px",marginLeft:"450px",width:"400px",height:"auto"}}>
        
         {users.map(u=>{
          
          //fetchConreqWhoseSenderIsCurrentUser(u.id);
          //fetchConreqWhoseReceiverIsCurrentUser(u.id);
            conreq1=connectionRequests.filter(req=>req.connectionRequestSender.id==user.id&&req.connectionRequestReceiver.id==u.id);
            conreq2=connectionRequests.filter(req=>req.connectionRequestSender.id==u.id&&req.connectionRequestReceiver.id==user.id);
            if(conreq1!=null&conreq1[0]!=null) conreq1=conreq1[0];
            if(conreq2!=null&conreq2[0]!=null) conreq2=conreq2[0];
         return _.isEqual(u,user)?
         <div></div>
         :!_.isEqual(u,user) && _.find(connectionsOfUser,u)?
          
           <ListGroupItem key={u.id} style={{display:"flex",justifyContent:"space-between"}}>
             <img style={{height:"40px",width:"40px"
         
       }} src={`data:image/*;base64,${u.userImage}`}/>
           <Link to={"/users/user/"+u.id} >{u.name+" "+u.surname}</Link>
           <Button variant="primary" sync="true" >Message</Button>
           <Button variant="danger" sync="true" onClick={()=>deleteConnection(u.id)}>Disconnect</Button>
            </ListGroupItem>
         :!_.isEqual(u,user) && !_.find(connectionsOfUser,u)&&_.isEqual(conreq1.connectionRequestSender,user)?
          
            <ListGroupItem key={u.id} style={{display:"flex",justifyContent:"space-between"}}>
              <img style={{height:"40px",width:"40px"
          
        }} src={`data:image/*;base64,${u.userImage}`}/>
            <Link to={"/users/user/"+u.id} >{u.name+" "+u.surname}</Link>
            <Button variant="danger" sync="true" onClick={()=>cancelConnectionRequest(u.id)}>Cancel Connection Request</Button>
             </ListGroupItem>
             :!_.isEqual(u,user) && !_.find(connectionsOfUser,u)&&_.isEqual(conreq2.connectionRequestReceiver,user)?
          
             <ListGroupItem key={u.id} style={{display:"flex",justifyContent:"space-between"}}>
               <img style={{height:"40px",width:"40px"
           
         }} src={`data:image/*;base64,${u.userImage}`}/>
             <Link to={"/users/user/"+u.id} >{u.name+" "+u.surname}</Link>
             <Button variant="success" sync="true" onClick={()=>acceptConnectionRequest(u.id)}>Accept Connection Request</Button>
             <Button variant="danger" sync="true" onClick={()=>refuseConnectionRequest(u.id)}>Refuse Connection Request</Button>
              </ListGroupItem>
          :!_.isEqual(u,user) && !_.find(user.connections,u)?
          
          <ListGroupItem key={u.id} style={{display:"flex",justifyContent:"space-between"}}>
            <img style={{height:"40px",width:"40px"
        
      }} src={`data:image/*;base64,${u.userImage}`}/>
          <Link to={"/users/user/"+u.id} >{u.name+" "+u.surname}</Link>
          <Button variant="warning" sync="true" onClick={()=>sendConnectionRequest(u.id)}>Connect</Button>
           </ListGroupItem>
           
           :
           <ListGroupItem key={u.id} style={{display:"flex",justifyContent:"space-between"}}>
            <img style={{height:"40px",width:"40px"
      }} src={`data:image/*;base64,${u.userImage}`}/>
          <Link to={"/users/user/"+u.id} >{u.name+" "+u.surname}</Link>
          <Button variant="success" sync="true" ></Button>
           </ListGroupItem>
          conreq1=null;
          conreq2=null;
    }
          
    
         
          
    )} 
     
   
    
    </ListGroup>
  )
}
