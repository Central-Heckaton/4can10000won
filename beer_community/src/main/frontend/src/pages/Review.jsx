import React from "react";
import ReviewButton from "../components/ReviewButton/ReviewButton";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import ReviewBox from "../components/ReviewBox/ReviewBox";
const Review = () => {
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <ReviewButton />
      <ReviewBox/>
      
    </>
  );
};

export default Review;
