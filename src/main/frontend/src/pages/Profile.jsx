import React, { useState } from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import ProfileBox from "../components/profileBox/ProfileBox";
import Footer from "../components/Footer/Footer";
import { useEffect } from "react";
import useLoginState from "../hooks/useLoginState";
const Profile = (props) => {
  let navigate = useNavigate();
  const loginState = useLoginState();
  useEffect(() => {
    if (loginState === false) {
      alert(
        "프로필 페이지를 확인하기 위해선 로그인이 필요합니다.\n로그인 페이지로 이동합니다."
      );
      navigate("/login");
    }
  });
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
