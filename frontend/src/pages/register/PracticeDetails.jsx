function PracticeDetails() {
  return (
    <div className="bg-white h-screen">
        <div className="bg-white  mx-10 my-4 text-white h-screen mb-12 ">
            <div className="h-4"></div>
            <div className="mx-40 border  border-black rounded-md">
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
                            <div className="w-20 h-20 bg-[#02685A] rounded-full"></div>
                            <div className="w-36 h-1 bg-[#02685A] my-2 "></div> 
                            <div className="w-23 h-23 bg-[#5AAC74] rounded-full">
                                <div className="w-20 h-20 bg-[#02685A] mx-1.5 my-1.5 rounded-full"></div>
                            </div>
                            <div className="w-24 h-1 bg-[#DDDDDD] my-2 rounded-lg mr-32"></div> 
                        </div>
                        <div className="flex flex-grow">
                            <p className="ml-60 px-2 text-[#02685A] font-semibold text-xl">Link HPR</p>
                            <p className="ml-24 text-[#02685A] font-semibold text-xl">Account Details</p>
                            <p className="ml-16 px-4 text-[#02685A] font-semibold text-xl">Practice Deatils</p>
                        </div>
                    </div>
                    <div className="w-80 p-4"></div>
                </div>


                <div className="flex px-32 bg-white mt-10">
                    <p className="font-semibold py-1.5 text-[#5F5F5F] text-lg">Hello Doc, It is important to fill all the details to use the platform !</p> 
                </div>
                
                
                <div className="mt-4 flex px-32">
                    <p className="text-sm mr-32 font-semibold text-[#787887]">Clinic id*</p>
                    <p className="text-sm mx-80 font-semibold text-[#787887]">Clinic Name*</p>
                    <p className="text-sm ml-auto mr-28 font-semibold text-[#787887]">Clinic Specialization*</p>
                </div>

                <div className="mt-3 px-32 flex">
                    <input className = "h-10 mr-1.5 rounded-md" type="text" />
                    <input className = "h-10 mx-64 rounded-md" type="text" />
                    <input className = "h-10 mr-1.5 ml-auto rounded-md" type="text" />
                </div>

                <div className="mt-4 flex px-32">
                    <p className="text-sm mr-32 font-semibold text-[#787887]">Clinic Address*</p>
                    <p className="text-sm mx-80 font-semibold text-[#787887]">Town/City*</p>
                </div>

                <div className="mt-3 px-32 flex">
                    <input className = "h-10 w-1/2 rounded-md" type="text" />
                    <input className = "h-10 ml-auto rounded-md" type="text" name="" id="" />
                </div>
                
                <div className="mt-4 flex px-32">
                    <p className="text-sm mr-36 font-semibold text-[#787887]">State*</p>
                    <p className="text-sm mx-80 font-semibold text-[#787887]">Pincode*</p>
                    <p className="text-sm ml-auto mr-48 font-semibold text-[#787887]">GSTIN*</p>
                </div>

                <div className="mt-3 flex px-32">
                    <input className = "h-10 mr-1.5 rounded-md" type="text" />
                    <input className = "h-10 mx-64 rounded-md" type="text" />
                    <input className = "h-10 rounded-md ml-auto" type="text" />
                </div>
                
                <div className="w-full h-0.5 bg-[#DDDDDD] mt-20 my-2 "></div> 

                <div className="flex items-center justify-center my-5">
                    <button className="bg-[#FFA000] block font-bold w-56 h-12 text-xl rounded-md mx-auto">SUBMIT</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default PracticeDetails