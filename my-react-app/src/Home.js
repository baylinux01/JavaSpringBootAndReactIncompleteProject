import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import _ from "lodash";
import { Table,Button, ListGroup } from 'react-bootstrap';


export default function Home(
  {group,setgroup,groups,setGroups,signupformsubmitresult,setSignupformsubmitresult,
    user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}
  ) {
  

  // function fetchGroups(){
  //   axios.defaults.baseURL="http://localhost:8083";
  //   axios.get("/groups/getallgroups").then((response)=>
  //     {setGroups([...response.data]);});
  // }

  // function fetchUser(){
  //   axios.defaults.baseURL="http://localhost:8083";
  //   axios.get("/users/getoneuserbyid",{params:{userId:user.id}})
  //   .then((response)=>{setUser({...response.data})});
  // }
  function fetchGroups(){
    axios.defaults.baseURL="http://localhost:8083";
    axios.get("/groups/getallgroups").then((response)=>
      {setGroups([...response.data]);});
  }
  useEffect(()=>{
    
    
    fetchGroups();},[groups]);


  function deleteGroup(groupId)
    {
        
        
      //axios kütüphanesi npm install axios kodu ile indirilebilir.
     
     axios.defaults.baseURL="http://localhost:8083";
     axios.delete("/groups/deletegroupbyid",{params:{groupId:groupId}});
     fetchGroups();
   
     
     
    }

    function leaveGroup(groupId,userId)
      {
          
          
        //axios kütüphanesi npm install axios kodu ile indirilebilir.
        //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
        //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
       const qs=require('qs');
       axios.defaults.baseURL="http://localhost:8083";
       axios.post("/groups/exitgroup",qs.stringify({groupId:groupId,userId:userId}));
       fetchGroups();
     
       
      }

 

  
  return (
    
    <div style={{ marginTop:"100px",marginLeft:"550px",
    width:"400px",minHeight:"500px",height:"auto",textDecoration:"none"}}>
      {
      Object.keys(user).length!==0 ?
      <div>
        {groups.filter(group=>
      
      _.isEqual(group.owner,user)).length>0?
        <div>Kurucusu olduğunuz gruplar:</div>:<div></div>}
      <div> {groups.filter(group=>
      
         _.isEqual(group.owner,user)
        
  ).map(g=>
 
    <div key={g.id}>
           
           <Table striped bordered hover>
      <thead>
     
      </thead>
      <tbody>
        <tr>
        
          <td style={{textAlign:"left",LineHeight:"50px",
            textDecoration:"none",width:"60%",borderRight:"none"
          }}><Link to={"/groups/group/"+g.id} >{g.name}</Link></td>
           
           <td style={{display:"flex",justifyContent:"right",
            alignItems:"center",textDecoration:"none",width:"auto",borderLeft:"none"
          }}><Button variant="danger" 
          sync="true" 
          onClick={()=>deleteGroup(g.id)}>Delete Group</Button></td>
          </tr>
       
       </tbody>
     </Table>
          
          </div>
  )}
      </div>
      {groups.filter(group=>
      
      !_.isEqual(group.owner,user) && _.find(group.members,user)).length>0?
      <div>Üye olduğunuz gruplar:</div>:<div></div>}
      <div> {groups.filter(group=>
      
      !_.isEqual(group.owner,user) && _.find(group.members,user)
     
).map(g=>

 <div key={g.id}>
    
           <Table striped bordered hover>
      <thead>
     
      </thead>
      <tbody>
        <tr>
        
          <td style={{textAlign:"left",LineHeight:"50px",
            textDecoration:"none",width:"60%",borderRight:"none"
          }}><Link to={"/groups/group/"+g.id}>{g.name}</Link></td>
          
           
           <td style={{display:"flex",justifyContent:"right",
            alignItems:"center",textDecoration:"none",width:"auto",borderLeft:"none"
          }}><Button variant="warning" 
          sync="true" 
          onClick={()=>leaveGroup(g.id,user.id)}>Leave Group</Button></td>
          
        </tr>
       
      </tbody>
    </Table>
       </div>
)}
   </div>
      <Link to={"/groupcreationpage"}><Button variant="primary" 
          >Create New Group</Button></Link>
      </div>
       : <div>Home</div>
    }
    </div>
    
    
   
  )
}
