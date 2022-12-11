import React, { useState, useEffect } from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import ProfileBox from "../components/profileBox/ProfileBox";
import Footer from "../components/Footer/Footer";
import useLoginState from "../hooks/useLoginState";
import axios from "axios";
const Profile = (props) => {
  let navigate = useNavigate();
  const loginState = useLoginState();
  const getUserData = async () => {
    try{
      let response = await axios.get("/api/loginstate");
      console.log("response: ", response);
    } catch (error){
      console.log("error: ", error);
      console.log("error.response: ", error.response);
      if (error.response.status === 403){
        if(window.confirm("프로필 페이지를 확인하기 위해선 로그인이 필요합니다.\n로그인 페이지로 이동합니다.")){
          navigate("/login", {});
        } else {
          navigate("/", {});
        }
      }
    }
  };
  useEffect(() => {
    getUserData();
    // if (loginState === false) {
    //   navigate("/login", {});
    // }
  }, []);
  return (
    <>
      <div>
        <Nav navigate={navigate} />
        <ProfileBox />
      </div>
      <Footer />
    </>
  );
};

export default Profile;

