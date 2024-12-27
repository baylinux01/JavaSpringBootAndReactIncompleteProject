import React, { useEffect,useState } from 'react';
import {useParams,Link} from "react-router-dom";
import axios from "axios";


export default function User({password,setPassword,user2,setUser2,user,setUser}) {
    const{user2Id}=useParams();
    
    const fetchUser2=()=>{
      axios.defaults.baseURL="http://localhost:8080";
      return axios.get("/users/getoneuserbyid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:user2Id}})
      .then((response)=>{setUser2(response.data)});
    }

   useEffect(()=> {
    //fetchGroup();
    //fetchComments();
    //fetchMembers();
    if(localStorage.getItem("user")!=null)
      {
        fetchUser2();
      }
    // fetch("/groups/getonegroupbyid?groupId="+groupId).then(response=>{return response.json()})
    // .then(data=>{setGroup(data)})
 },[])
  return (
    <div>
    {Object.keys(user2).length!==0?
    <img style={{position:"absolute",height:"100px",width:"100px",
      top:"50px",left:"0px"
    }} src={`data:image/*;base64,${user2.userImage}`}/>:
    <div>In order to see user profile please log in</div>
  }
    <div style={{position:"absolute",
      top:"50px",left:"120px"
    }} >{user2.name} {user2.surname}</div>
    </div>
  )
}
