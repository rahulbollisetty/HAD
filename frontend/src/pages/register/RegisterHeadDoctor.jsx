import React, { useEffect } from 'react'
import LinkHPR from './LinkHPR'
import axios from '../../api/axios'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
const RegisterHeadDoctor = () => {
  const navigate = useNavigate();

  useEffect(()=>{

    const isHeadDoctorPresent = async()=>{
      try{
        const resp = await axios.get("/auth/isHeadDoctorPresent");
        console.log(resp);
      }
      catch(error){
        console.log(error.response)
        if(error.response.status === 409){
          navigate("/login");
          toast.error(error.response.data);
        }
      }
    }
    isHeadDoctorPresent();
  },[])
  return (
    <LinkHPR isHead={true}/>
  )
}

export default RegisterHeadDoctor