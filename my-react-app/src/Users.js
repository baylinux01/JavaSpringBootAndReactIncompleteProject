import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import _ from "lodash";
import { Table,Button, ListGroup, ListGroupItem } from 'react-bootstrap';

export default function Users({password,setPassword,group,setgroup,groups,setGroups,signupformsubmitresult,setSignupformsubmitresult,
    users,setUsers,user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}) {

        function fetchUser(){
            axios.defaults.baseURL="http://localhost:8080";
            
            axios.get("/users/getoneuserbyid",{auth: {username: user.username,password: password},params:{userId:localStorage.getItem("id")}})
            .then((response)=>{setUser({...response.data})});
          }
      function fetchUsers(){
        axios.defaults.baseURL="http://localhost:8080";
        
        axios.get("/users/getallusers")//,{auth: {username: user.username,password: password}})
        .then((response)=>{setUsers([...response.data])});
      }
     useEffect(() => {
        if(localStorage.getItem("id")!=null)
            {
              fetchUser();
            }
       fetchUsers();
     
       
     }, [])
     

  return (
    <ListGroup style={{marginTop:"100px",marginLeft:"450px",width:"400px",height:"auto"}}>
        
         {users.map(u=>
         
         
         _.isEqual(u,user)?
         <div></div>
         
          :!_.isEqual(u,user) && !_.find(user.connections,u)?
          
          <ListGroupItem key={u.id} style={{display:"flex",justifyContent:"space-between"}}>
            <img style={{height:"40px",width:"40px"
        
      }} src={`data:image/*;base64,${u.userImage}`}/>
          <Link to={"/users/user/"+u.id} >{u.name+" "+u.surname}</Link>
          <Button variant="warning" sync="true" >Connect</Button>
           </ListGroupItem>
           :
           <ListGroupItem key={u.id} style={{display:"flex",justifyContent:"space-between"}}>
            <img style={{height:"40px",width:"40px"
      }} src={`data:image/*;base64,${u.userImage}`}/>
          <Link to={"/users/user/"+u.id} >{u.name+" "+u.surname}</Link>
          <Button variant="success" sync="true" ></Button>
           </ListGroupItem>
           
          
    )} 
     
   
    
    </ListGroup>
  )
}
