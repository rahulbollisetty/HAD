import React, { useEffect, useState } from "react";
import useAuth from "../hooks/useAuth";
import { jwtDecode } from "jwt-decode";

export default function TopBar() {
  const { auth } = useAuth();
  const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  const [fullName, setFullName] = useState("");
  useEffect(() => {
    setFullName(decoded?.name);
  }, []);
  return (
    <div className="h-[64px] bg-white w-full">
      <div className="w-full h-[4.5rem] flex flex-row-reverse p-2">
        <div className="flex items-center mr-[2em]">
          <div className="flex justify-center items-center h-4/5">
            <img
              className="rounded-full h-full object-contain"
              src="https://wallpapers.com/images/hd/handsome-giga-chad-hmsvijj0ji4dhedr.jpg"
              alt="profile photo"
            />
          </div>
          <div className="mx-2">
            <p className="text-[#787887] text-m font-semibold">{fullName}</p>
          </div>
        </div>
      </div>
    </div>
  );
}
