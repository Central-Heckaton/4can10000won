// Review.jsx
import React from "react";
import ReviewButton from "../components/ReviewButton/ReviewButton";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import ReviewBox from "../components/ReviewBox/ReviewBox";
import { useLocation } from "react-router-dom";

const Review = () => {
  let navigate = useNavigate();
  const location = useLocation();
  const id = location.state.id;
  console.log(id);

  return (
    <>
      <Nav navigate={navigate} />
      <ReviewButton />
      <ReviewBox id={id}/>
      
    </>
  );
};

export default Review;