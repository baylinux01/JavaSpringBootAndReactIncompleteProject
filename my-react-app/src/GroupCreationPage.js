import React, { useEffect,useState,useRef } from 'react';
import {useParams,useNavigate,Link, json} from "react-router-dom";
import axios, { formToJSON } from "axios";
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function GroupCreationPage({password,setPassword,user,setUser,groups,setGroups}) {

  const[newGroupName,setNewGroupName]=useState("");

  const navigate=useNavigate();

  function fetchGroups(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/groups/getallgroups").then((response)=>{setGroups([...response.data])});
  }

  function fetchUser(){
    axios.defaults.baseURL="http://localhost:8080";
    axios.get("/users/getoneuserbyid",{auth: {username: user.username,password: password},params:{userId:user.id}})
    .then((response)=>{setUser({...response.data})});
  }

  function createthegroup(e)
    {
        e.preventDefault();
        
      //axios kütüphanesi npm install axios kodu ile indirilebilir.
      //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.qs kullanmayınca veriler api'ya null gidiyor
      axios.defaults.baseURL="http://localhost:8080";
      const qs=require('qs');
      axios.post("/groups/creategroup", 
        qs.stringify( {ownerId: user.id,
        name: newGroupName})
      ,{
        auth: {
          username: user.username,
          password: password
        }
      });
      
      fetchGroups();
      fetchUser();
      
      navigate("/");
      //react'ta bir objenin null olup olmadığını kontrol etmek için Object.keys(obje).length===0 kullanılıyor.
      //react'ta objeyi setter ile güncellerken özel bir kullanım var
      //obje setObject(object=>({...object,...updatedValue})) şeklinde güncellenir.
       
    }

    return (
        <Form style={{width:"400px",height:"400px",marginTop:"150px",marginLeft:"450px"}} 
        sync="true" onSubmit={(e)=>createthegroup(e)}>
        {/* <Form action='http://localhost:8083/users/enteruser' method='post'> */}

        

      <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
        <Form.Label column sm={7}>
          Group Name
        </Form.Label>
        <Col sm={10}>
          <Form.Control type="text" placeholder="New Group Name" name="newGroupName" autoComplete="off" value={newGroupName} onChange={(e)=>setNewGroupName(e.target.value)}/>
        </Col>
      </Form.Group>

      
     
      

      <Form.Group as={Row} className="mb-3">
        <Col sm={{ span: 10, offset: 2 }}>
           <Button type="submit" >Create The Group</Button> 
        </Col>
      </Form.Group>
    </Form>
    )
  }