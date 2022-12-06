import React, { useState } from "react";
import axios from "axios";
import { useEffect } from "react";

const useLoginState = () => {
  const [isOnline, setIsOnline] = useState(null);

    const getLoginState = async () => {
      try {
        const response = await axios.get("/api/loginstate");
        console.log(response.status);
        setIsOnline(true);
      } catch (error) {
        console.log(error);
        if (error.response.status === 403) {
          setIsOnline(false);
        }
      }
    };
    getLoginState();


  return isOnline;
};

export default useLoginState;
