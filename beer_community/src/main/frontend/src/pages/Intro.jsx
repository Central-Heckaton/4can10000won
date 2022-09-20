import React from 'react';
import MainLoading from "../components/mainLoading/MainLoading";
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Intro = () => {
  const navigate = useNavigate();
  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/search");
    }, 2000);


    return () => {
      clearTimeout(timer);
    };
  }, []);


  return (
    <>
      <MainLoading />
    </>
  );
};

export default Intro;