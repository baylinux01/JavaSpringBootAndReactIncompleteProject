import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
export default function Navbar({user,setUser}) {

  

  return (
        <div style={{backgroundColor:"black",height:"50px",display:"flex",justifyContent:"space-between",paddingTop:"10px",paddingBottom:"10px"}}>
          <div style={{display:"flex",columnGap:"20px"}}>
          <Link to={"/"}style={{color:"red",textDecoration:"none",marginLeft:"10px"}}>Home</Link>
          <Link to={"/groups"}style={{color:"red",textDecoration:"none"}}>Groups</Link>
          </div>
          {Object.keys(user).length!==0 ?
          <div style={{display:"flex",columnGap:"20px"}}>
            <div style={{color:"red",textDecoration:"none"}}> {user.name+" "+user.surname}</div>
          <Link to={"/login"}style={{color:"red",textDecoration:"none",marginRight:"20px"}} onClick={()=>setUser({})} >Sign Out</Link>
          </div>
          :<div style={{display:"flex",columnGap:"20px"}}>
          <Link to={"/login"}style={{color:"red",textDecoration:"none"}}>Sign In</Link>
          <Link to={"/register"}style={{color:"red",textDecoration:"none",marginRight:"20px"}}>Sign Up</Link>
          </div>}
        </div>
  )
}

