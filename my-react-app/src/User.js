import React, { useEffect,useState } from 'react';
import {useParams,Link} from "react-router-dom";
import axios from "axios";


export default function User({user2,setUser2,user,setUser}) {
    const{user2Id}=useParams();
    
    const fetchUser=()=>{
      axios.defaults.baseURL="http://localhost:8083";
      return axios.get("/users/getoneuserbyid",{params:{userId:user2Id}})
      .then((response)=>{setUser2(response.data)});
    }

   useEffect(()=> {
    //fetchGroup();
    //fetchComments();
    //fetchMembers();
    fetchUser();
    // fetch("/groups/getonegroupbyid?groupId="+groupId).then(response=>{return response.json()})
    // .then(data=>{setGroup(data)})
 },[user2])
  return (
    <div>{user2.name} {user2.surname}</div>
  )
}
