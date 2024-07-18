import React, { useEffect,useState,useRef } from 'react';
import {useParams,useNavigate,Link, json} from "react-router-dom";
import axios, { formToJSON } from "axios";
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import 'bootstrap/dist/css/bootstrap.min.css';




export default function Login({signupformsubmitresult,setSignupformsubmitresult,user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}) 
{
  

    const[username,setUsername]=useState("");
    const[password,setPassword]=useState("");
    
    const navigate=useNavigate();

    
    
    const handlesigninformsubmit=(e)=>
    {
        e.preventDefault();
        
      //axios kütüphanesi npm install axios kodu ile indirilebilir.
      //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
      //qs kullanmayınca post isteklerinde veriler api'ya null gidiyor
     const qs=require('qs');
     axios.defaults.baseURL="http://localhost:8083";
     axios.post("/users/enteruser",qs.stringify({username:username,password:password}))
     .then((response)=>
      {
      setUser((user)=>({...response.data}));
      console.log(user);
      if(Object.keys(response.data).length !==0 ){navigate("/");}
      if(Object.keys(response.data).length ===0 )
      {setUnsuccessfulsignin(true); setSignupformsubmitresult("");}
      //react'ta bir objenin null olup olmadığını kontrol etmek için Object.keys(obje).length===0 kullanılıyor.
      //react'ta objeyi setter ile güncellerken özel bir kullanım var
      //obje setObject((object)=>({...updatedValue})) şeklinde güncellenir.
       });
    }

    

  return (
    <div style={{display:"flex",justifyContent:"center"}}>
        <div style={{width:"500px",height:"500px",marginTop:"100px"}}>
        {signupformsubmitresult ==='Registration is successful'? <div style={{width:"300px",height:"50px"}}>{signupformsubmitresult}. You can login!</div>:<div></div>}
        {unsuccessfulsignin ===true? <div style={{width:"300px",height:"50px"}}>Login is unsuccessful. You can try again!</div>:<div></div>}
        <Form onSubmit={(e)=>handlesigninformsubmit(e)}>
        {/* <Form action='http://localhost:8083/users/enteruser' method='post'> */}

        

      <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
        <Form.Label column sm={2}>
          Username
        </Form.Label>
        <Col sm={10}>
          <Form.Control type="text" placeholder="Username" name="username" autoComplete="off" value={username} onChange={(e)=>setUsername(e.target.value)}/>
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="formHorizontalPassword">
        <Form.Label column sm={2}>
          Password
        </Form.Label>
        <Col sm={10}>
          <Form.Control type="password" placeholder="Password" name="password" autoComplete="off" value={password} onChange={(e)=>setPassword(e.target.value)}/>
        </Col>
      </Form.Group>
      
     
      

      <Form.Group as={Row} className="mb-3">
        <Col sm={{ span: 10, offset: 2 }}>
           <Button type="submit" >Sign In</Button> 
        </Col>
      </Form.Group>
    </Form>
    </div>
    </div>
    
  )
}
