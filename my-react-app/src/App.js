import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import logo from './logo.svg';
import './App.css';
import Navbar from './Navbar';
import { BrowserRouter,Routes,Switch,Route } from 'react-router-dom';
import Home from './Home';
import Groups from './Groups';
import Group from "./Group";
import User from "./User";
import Register from "./Register";
import Login from "./Login";
import GroupCreationPage from './GroupCreationPage';
import _ from "lodash";

//react'ta lodash kütüphanesi objeleri karşılaştırmaya veya
//bir dizi içerisinde belli bir objenin olup olmadığını anlamaya yarar
//lodash kütüphanesi npm install lodash kodu ile indirilebilir
//sonra sayfa başında import _ from lodash yaparak kullanılabilir
//_.isEqual() ve _.find metotları lodah kütüphanesine aittir.

//axios kütüphanesi npm install axios kodu ile indirilebilir.
//qs kullanmak için önce npm i qs yazarak indirmek gerekiyor.
//qs kullanmayınca post isteklerinde veriler api'ya null gidiyor

//react'ta bir objenin null olup olmadığını kontrol etmek için Object.keys(obje).length===0 kullanılıyor.
//react'ta objeyi setter ile güncellerken özel bir kullanım var
//obje setObject((object)=>({...updatedValue})) şeklinde güncellenir.

//react'ta bir arrayi güncellerken setArray([...response.data]) şeklinde güncellemek gerekiyor
//aksi takdirde api'dan obje geldiği için array objeye dönüşebiliyor. bu da hata fırlatıyor.

function App() {

  const[unsuccessfulsignin,setUnsuccessfulsignin]=useState(false);
  const [signupformsubmitresult,setSignupformsubmitresult]=useState("");
  const [user,setUser]=useState({});
  const[groups,setGroups]=useState([]);
  const [group,setGroup]=useState({});
  const [user2,setUser2]=useState({});
  const[bannedUsersOfUser,setBannedUsersOfUser]=useState({});
  

  function fetchGroups(){
    axios.defaults.baseURL="http://localhost:8083";
    axios.get("/groups/getallgroups").then((response)=>
      {setGroups([...response.data])});
  }

 
    useEffect(()=> {
      
      

      fetchGroups();},[groups])


  return (
    <div>
    <BrowserRouter>
    <Navbar user={user} setUser={setUser}></Navbar>
    
    <Routes>

    <Route exact path="/" element={<Home 
    group={group} setGroup={setGroup} groups={groups} setGroups={setGroups} unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult}>
    </Home>}></Route>

    <Route exact path="/groups" element={<Groups 
    groups={groups} setGroups={setGroups} 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult}>
    </Groups>}></Route>

    <Route exact path="/groups/group/:groupId" element={<Group 
    group={group} setGroup={setGroup}
    user={user} setUser={setUser}
    bannedUsersOfUser={bannedUsersOfUser} setBannedUsersOfUser={setBannedUsersOfUser}>
    </Group>}></Route>

    <Route exact path="/groupcreationpage" 
    element=
    {
    <GroupCreationPage 
    user={user} setUser={setUser} groups={groups} setGroups={setGroups}>
    </GroupCreationPage>
    }>
    </Route>

    <Route exact path="/users/user/:user2Id" element={<User 
    user2={user2} setUser2={setUser2} 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult}>
    </User>}></Route>

    <Route exact path="/register" element={<Register 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult} >
    </Register>}></Route>

    <Route exact path="/login" element={<Login 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} signupformsubmitresult={signupformsubmitresult} 
    setSignupformsubmitresult={setSignupformsubmitresult}>
    </Login>}></Route>
    
    </Routes>
    </BrowserRouter>
    </div>
    
    
  );
}

export default App;
