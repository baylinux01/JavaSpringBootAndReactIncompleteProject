import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import _ from "lodash";
import { Modal,ListGroup, ListGroupItem,Button } from 'react-bootstrap';

//react'ta lodash kütüphanesi objeleri karşılaştırmaya veya
//bir dizi içerisinde belli bir objenin olup olmadığını anlamaya yarar
//lodash kütüphanesi npm install lodash kodu ile indirilebilir
//sonra sayfa başında import _ from lodash yaparak kullanılabilir
//_.isEqual() ve _.find metotları lodah kütüphanesine aittir.

export default function Groups(
  {password,setPassword,groups,setGroups,signupformsubmitresult,setSignupformsubmitresult,
    user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}
  ) {

  
    const [showPopUp2,setShowPopUp2]=useState(false);
  const [newGroupName,setNewGroupName]=useState("");

  function fetchUser(){
    axios.defaults.baseURL="http://localhost:8080";
    
    axios.get("/users/getoneuserbyid",{auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{userId:localStorage.getItem("id")}})
    .then((response)=>{setUser({...response.data})});
  }
  function fetchGroups(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/groups/getallgroups").then((response)=>
      {setGroups([...response.data]);});
  }

  // function fetchUser(){
  //   axios.defaults.baseURL="http://localhost:8083";
  //   axios.get("/users/getoneuserbyid",{params:{userId:user.id}})
  //   .then((response)=>{setUser({...response.data})});
  // }
    const[count,setCount]=useState(0);
    
    useEffect(()=> {
      
      fetchUser();
      fetchGroups();
    },[])

    function deleteGroup(groupId)
      {
          
          
        //axios kütüphanesi npm install axios kodu ile indirilebilir.
      
       axios.defaults.baseURL="http://localhost:8080";
       axios.delete("/groups/deletegroupbyid",
        {auth: {username: localStorage.getItem("username"),password: localStorage.getItem("password")},params:{groupId:groupId}});
      
      fetchGroups();
       
       
      }

      function joinGroup(groupId,userId)
        {
            
            
          //axios kütüphanesi npm install axios kodu ile indirilebilir.
          //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
          //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
        

         axios.defaults.baseURL="http://localhost:8080";
      const qs=require('qs');
      axios.post("/groups/beamemberofgroup", 
        qs.stringify( {groupId: groupId
        })
      ,{
        auth: {
          username: localStorage.getItem("username"),
          password: localStorage.getItem("password")
        }
      });
         fetchGroups();
        }

        function leaveGroup(groupId,userId)
          {
              
              
            //axios kütüphanesi npm install axios kodu ile indirilebilir.
            //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
            //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
           

           axios.defaults.baseURL="http://localhost:8080";
      const qs=require('qs');
      axios.post("/groups/exitgroup", 
        qs.stringify({groupId: groupId
        })
      ,{
        auth: {
          username: localStorage.getItem("username"),
          password: localStorage.getItem("password")
        }
      });
           fetchGroups();
          }
          function createthegroup()
          {
              
              
            //axios kütüphanesi npm install axios kodu ile indirilebilir.
            //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.qs kullanmayınca veriler api'ya null gidiyor
            axios.defaults.baseURL="http://localhost:8080";
            const qs=require('qs');
            axios.post("/groups/creategroup", 
              qs.stringify( {ownerId: user.id,
              name: newGroupName})
            ,{
              auth: {
                username: localStorage.getItem("username"),
                password: localStorage.getItem("password")
              }
            });
            
            fetchGroups();
            fetchUser();
            setShowPopUp2(false);
            window.history.go(0);
            
            
            
            //react'ta bir objenin null olup olmadığını kontrol etmek için Object.keys(obje).length===0 kullanılıyor.
            //react'ta objeyi setter ile güncellerken özel bir kullanım var
            //obje setObject(object=>({...object,...updatedValue})) şeklinde güncellenir.
             
          }
  
   
    if(Object.keys(user).length !==0)
  {
  return (
    <ListGroup style={{marginTop:"100px",marginLeft:"450px",width:"400px",height:"auto"}}>
        
         {groups.map(group=>
         
         
         _.isEqual(group.owner,user)?
         <ListGroupItem key={group.id} style={{display:"flex",justifyContent:"space-between"}}>
         <Link to={"/groups/group/"+group.id} >{group.name}</Link>
         <Button variant="danger" sync="true" onClick={()=>{deleteGroup(group.id);}} >Delete Group</Button>
          </ListGroupItem>
          :!_.isEqual(group.owner,user) && _.find(group.members,user)?
          
          <ListGroupItem key={group.id} style={{display:"flex",justifyContent:"space-between"}}>
          <Link to={"/groups/group/"+group.id} >{group.name}</Link>
          <Button variant="warning" sync="true" onClick={()=>leaveGroup(group.id,user.id)}>Leave Group</Button>
           </ListGroupItem>
           :
           <ListGroupItem key={group.id} style={{display:"flex",justifyContent:"space-between"}}>
          <Link to={"/groups/group/"+group.id} >{group.name}</Link>
          <Button variant="success" sync="true" onClick={()=>joinGroup(group.id,user.id)}>Join Group</Button>
           </ListGroupItem>
           
          
    )} 
     
    <ListGroupItem>
    <Modal show={showPopUp2} onHide={()=>setShowPopUp2(false)}>
    <Modal.Header>
      <Modal.Title>Create New Group</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <input type='text' id='newmessageContent' onChange={(e)=>setNewGroupName(e.target.value)}></input>
      
    </Modal.Body>
    <Modal.Footer>
    <Button variant='primary' onClick={()=>{createthegroup()}}>Create Group</Button>
    <Button variant='primary' onClick={()=>{setShowPopUp2(false)}}>Close Pop-Up</Button>
    </Modal.Footer>
    </Modal>
      <Button variant="primary" onClick={()=>setShowPopUp2(true)}>Create New Group</Button>
    </ListGroupItem>
    
    </ListGroup>

  )
}else
{
  return(
    <ListGroup style={{marginTop:"100px",marginLeft:"450px",width:"400px",height:"auto"}}>
        
         {groups.map(group=>
         
         
        
           <ListGroupItem>
          <Link>{group.name}</Link>
           </ListGroupItem>
           
          
    )} 
    </ListGroup>
  )
}
}
