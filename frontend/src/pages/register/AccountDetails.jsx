function AccountDetails() {
  return (
    <div className="bg-white h-screen">
        <div className="bg-white mx-10 my-4 text-white h-screen mb-12 ">
            <div className="h-4"></div>
            <div className="mx-40 m-10 border  border-black rounded-md">
                <div className="my-5 h-20 bg-[#98a2a3] flex items-center justify-center text-3xl font-semibold text-white py-5">
                  Welcome to MediSync
                </div>

                <div className="h-40 px-32 flex items-center">
                    <div className="w-80 p-4"></div>
                    <div className="bg-white">
                        <div className="h-full p-4 items-center flex flex-grow">
                            
                            <div className="w-24 h-1 bg-[#02685A] my-2 rounded-lg ml-32"></div> 
                            <div className="w-20 h-20 bg-[#02685A] rounded-full"></div>
                            <div className="w-36 h-1 bg-[#02685A] my-2 "></div> 
                            <div className="w-23 h-23 bg-[#5AAC74] rounded-full">
                                <div className="w-20 h-20 bg-[#02685A] mx-1.5 my-1.5 rounded-full"></div>
                            </div>
                            <div className="w-36 h-1 bg-[#DDDDDD] my-2 "></div> 
                            <div className="w-20 h-20 bg-[#DDDDDD] rounded-full"></div>
                            <div className="w-24 h-1 bg-[#DDDDDD] my-2 rounded-lg mr-32"></div> 
                        </div>
                        <div className="flex flex-grow">
                            <p className="ml-60 px-2 text-[#02685A] font-semibold text-xl">Link HPR</p>
                            <p className="ml-24 text-[#02685A] font-semibold text-xl">Account Details</p>
                            <p className="ml-16 px-4 text-[#7F8C8D] font-semibold text-xl">Practice Deatils</p>
                        </div>
                    </div>
                    <div className="w-80 p-4"></div>
                </div>


                <div className="flex px-32 bg-white mt-10">
                    <p className="font-semibold py-1.5 text-[#5F5F5F] text-lg">Hello Doc, It is important to fill all the details to use the platform !</p>
                </div>
                
                
                <div className="my-12 bg-white px-32 text-black">
                    <div className="flex mb-3">
                        <p className="text-sm font-semibold mr-36 text-[#787887]">Name*</p>
                        <p className="text-sm font-semibold mx-80 text-[#787887]">Mobile Number*</p>
                        <p className="text-sm font-semibold mx-16 text-[#787887]">Email Address*</p>
                    </div>
                    <input  className = "rounded-md mr-1.5" type="text" />
                    <input className="rounded-md mx-64" type="text" />
                    <input className="rounded-md ml-1.5" type="text" />

                    <p className="text-sm mt-10 mb-3 font-semibold mr-36 text-[#787887]">Registration Detials*</p>
                    <input className="rounded-md " type="text" />

                    <div className="flex mb-3">
                        <p className="text-sm mt-10 mr-4 font-semibold text-[#787887]">Gender*</p>
                        <label className="text-sm mt-10 ml-auto font-semibold text-[#787887] mr-12" htmlFor="birthday">Date Of Birth(MM/DD/YYYY)*</label>
                    </div>
                    <div className="flex items-center">
                        <input className="mt-1" type="radio" id="Male" value="Male" name="gender" />
                        <label className="ml-2 text-sm font-semibold text-[#787887]" htmlFor="Male">Male</label>

                        <input className="mt-1 ml-4" type="radio" id="Female" value="Female" name="gender" />
                        <label className="ml-2 text-sm font-semibold text-[#787887]" htmlFor="Female">Female</label>

                        <input className="mt-1 ml-4" type="radio" id="Others" value="Others" name="gender" />
                        <label className="ml-2 text-sm font-semibold text-[#787887]" htmlFor="Others">Others</label>
                    
                        <div className="flex mb-3 mr-16 rounded-md ml-auto">
                            <div className="flex items-center">
                                <input type="date" id="birthday" name="birthday" className="mr-4" />
                            </div>
                        </div>
                    </div>

                    <div className="w-full h-0.5 bg-[#DDDDDD] mt-10 my-2 "></div> 

                    <div className="flex mb-3">
                        <p className="text-sm font-semibold mr-40 text-[#787887]">Address Line*</p>
                        <p className="text-sm font-semibold mx-64 text-[#787887]">Town / City*</p>
                        <p className="text-sm font-semibold ml-40 text-[#787887]">Pincode*</p>
                    </div>
                    <input className="mb-3 rounded-md mr-1.5" type="text" />
                    <input className="mb-3 rounded-md mx-64" type="text" />
                    <input className="mb-3 rounded-md ml-1.5" type="text" />

                    <p className="mb-3 text-sm font-semibold text-[#787887]">State*</p>
                    <input className="rounded-md mb-3" type="text" />

                    <div className="w-full h-0.5 bg-[#DDDDDD] mt-10 my-2 "></div> 

                    <div className="flex mb-3">
                        <p className="text-sm font-semibold mr-20 text-[#787887]">Set Username*</p>
                        <p className="text-sm font-semibold mx-80 text-[#787887]">Set Password*</p>
                    </div>
                    <input className="rounded-md mr-1.5 mb-3" type="text" />
                    <input className="rounded-md mx-64" type="text" />
                </div>
                
                <div className="w-full h-0.5 bg-[#DDDDDD] mt-20 my-2 "></div> 
                <div className="pl-80 my-5 pr-32">
                    <button className="bg-[#FFA000] block font-bold w-56 h-12 text-xl rounded-md ml-auto">Next</button>
                </div>
            </div>
        </div>

    </div>
  )
}

export default AccountDetails;