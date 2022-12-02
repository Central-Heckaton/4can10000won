import React from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import ProfileBox from "../components/profileBox/ProfileBox";
import Footer from "../components/Footer/Footer";
const Profile = () => {
  let navigate = useNavigate();
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
