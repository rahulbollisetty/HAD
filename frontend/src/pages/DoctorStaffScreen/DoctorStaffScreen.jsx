import Sidebar from "../Sidebar";
import TopBar from "../TopBar";
import StaffTabs from "./components/StaffTabs";

export const DoctorStaffScreen = () => {
  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
        <Sidebar />
      </div>
      <div className="basis-full bg-[#F1F5FC] overflow-hidden">
        <div className="flex flex-col h-full ">
          {/* <div className="h-[64px] w-full pb-16 bg-white"></div> */}
          <TopBar />
          <div className="bg-white grow m-3">
            <div className="flex flex-col">
              <div className="">
                <div className="flex mt-6 mb-8  justify-center">
                  <StaffTabs />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
