import LinkHPR from "./LinkHPR";
import { useLocation,useNavigate } from "react-router-dom";
import { useEffect } from "react";
import { toast } from "react-toastify";

const Register = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { token } = location.state || {};
  console.log(token);
  useEffect(()=>{
    if(!token){
      navigate("/login")
      toast.error("Invalid Token")
    }
  },[])
  return <LinkHPR />;
};
export default Register;
