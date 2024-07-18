import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import 'bootstrap/dist/css/bootstrap.min.css';


export default function Register({signupformsubmitresult,setSignupformsubmitresult,user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}) {
  
const[name,setName]=useState("");
const[surname,setSurname]=useState("");
const[username,setUsername]=useState("");
const[password,setPassword]=useState("");
const[birthDate,setBirthDate]=useState("");


const navigate=useNavigate();

useEffect(()=>{setSignupformsubmitresult("");setUnsuccessfulsignin(false)},[]);

function handlesignupformsubmit(e,signupformsubmitresult){
    e.preventDefault();
    //axios kütüphanesi npm install axios kodu ile indirilebilir.
    //qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.qs kullanmayınca veriler api'ya null gidiyor
     const qs=require('qs');
     axios.defaults.baseURL="http://localhost:8083";
     axios.post("/users/adduser",qs.stringify({name:name,surname:surname,username:username,password:password,birthdate:birthDate}))
    .then((response)=>{setSignupformsubmitresult(response.data);
      if(response.data=='Registration is successful'){navigate("/login")}});
    
    setName("");
    setSurname("");
    setUsername("");
    setPassword("");


    
    
    
}


  return (
    <div style={{display:"flex",justifyContent:"center"}}>
        <div style={{width:"500px",height:"500px",marginTop:"100px"}}>
        {signupformsubmitresult !=null && signupformsubmitresult!=='Registration is successful' ? <div style={{width:"300px",height:"50px"}}>{signupformsubmitresult}</div>:<div></div>}
        <Form sync="true" onSubmit={e=>handlesignupformsubmit(e,signupformsubmitresult)}>
        {/* <Form action='http://localhost:8083/users/adduser' method='post'> */}

        <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
        <Form.Label column sm={2}>
          Name
        </Form.Label>
        <Col sm={10}>
          <Form.Control type="text" placeholder="Name" name="name" autoComplete="off" value={name} onChange={(e)=>setName(e.target.value)} />
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
        <Form.Label column sm={2}>
          Surname
        </Form.Label>
        <Col sm={10}>
          <Form.Control type="text" placeholder="Surname" name="surname" autoComplete="off" value={surname}onChange={(e)=>setSurname(e.target.value)}/>
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
        <Form.Label column sm={2}>
          Birth Date
        </Form.Label>
        <Col sm={10}>
          <Form.Control type="date" placeholder="Birth Date" name="birthdate" autoComplete="off" value={birthDate}onChange={(e)=>setBirthDate(e.target.value)}/>
        </Col>
      </Form.Group>


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
           <Button type="submit" >Sign Up</Button> 
        </Col>
      </Form.Group>
    </Form>
    </div>
    </div>
  )
}
