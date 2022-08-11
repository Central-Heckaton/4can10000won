import React from "react";
import ReviewButton from "../components/ReviewButton/ReviewButton";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
const Review = () => {
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <ReviewButton />
      
    </>
  );
};

export default Review;
