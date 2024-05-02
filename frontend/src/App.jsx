import Login from "./pages/Login/Login";
import Register from "./pages/register/Register";
import PracticeDetails from "./pages/register/PracticeDetails";
import { PatientScreen } from "./pages/PatientScreen/PatientScreen";
import LinkHPR from "./pages/register/LinkHPR";
import AccountDetails from "./pages/register/AccountDetails";
import { Router, Route, Routes } from "react-router-dom";
import Layout from "./pages/Layout";
import Unauthorized from "./pages/exceptions/Unauthorized";
import RequireAuth from "./routes/RequireAuth";
import PersistLogin from "./PersistLogin";
import { ToastContainer, Bounce } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { DoctorStaffScreen } from "./pages/DoctorStaffScreen/DoctorStaffScreen";

import AddRecords from "./pages/PatientScreen/components/AddRecords";
import AddAppointmentForm from "./pages/PatientScreen/forms/AddAppointmentForm";
import CalendarScreen from "./pages/calendar/CalendarScreen";
import SettingsScreen from "./pages/Settings/SettingsScreen";
// import AddStaffForm from "./pages/DoctorStaffScreen/forms/AddStaffForm";
import StaffDetail from "./pages/DoctorStaffScreen/components/StaffDetail";
import StaffDetails from "./pages/register/StaffDetails";
import AddDoctorForm from "./pages/DoctorStaffScreen/forms/AddDoctorForm";
import StaffList from "./pages/DoctorStaffScreen/components/StaffList";
import VerifyEmail from "./pages/DoctorStaffScreen/forms/VerifyEmail";
import DoctorEdit from "./pages/Settings/components/DoctorEdit";
import EditTab from "./pages/Settings/components/EditTab";
import StaffEdit from "./pages/Settings/components/StaffEdit";
import { Settings } from "@mui/icons-material";
import BaseScreen from "./pages/BaseScreen";
import AllPatientList from "./pages/PatientScreen/components/AllPatientList";
import ForgotPassword from "./pages/Login/ForgotPassword";
function App() {
  return (
    <div>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
        transition={Bounce}
      />
      <Routes>
        <Route path="/" element={<Layout />}>
          {/* public routes */}
          <Route path="login" element={<Login />} />
          <Route path="register/doctor" element={<Register />} />
          <Route path="register/staff" element={<StaffDetails />} />
          <Route path="register/" element={<VerifyEmail />} />
          <Route path="auth/forgotPassword" element={<ForgotPassword />} />
          {/* <Route path="linkpagae" element={<LinkPage />} /> */}
          <Route path="unauthorized" element={<LinkHPR />} />
          <Route path="forgotPassword" element={<ForgotPassword />} />

          <Route path="settings/head_doctor" element={<EditTab />} />

          {/* we want to protect these routes */}
          <Route element={<PersistLogin />}>
            <Route element={<RequireAuth allowedRoles={["DOCTOR", "STAFF"]} />}>
              <Route path="register/faculty" element={<AddDoctorForm />} />
              <Route
                path="/"
                element={<BaseScreen active_tab={"All Patient"} />}
              />
              <Route path="register/HPR" element={<PracticeDetails />} />

              <Route
                path="/settings"
                element={<BaseScreen active_tab={"Settings"} />}
              />
              <Route
                path="/stafflist"
                element={<BaseScreen active_tab={"Doctor List"} />}
              />
              <Route
                path="/calendar"
                element={<BaseScreen active_tab={"Calendar"} />}
              />

              {/* <Route path="/calendar" element={<BaseScreen active_tab={"Calendar"} />} /> */}

              <Route
                path="/patientScreen/:id"
                element={<BaseScreen active_tab={"All Patient"} />}
              />

              <Route
                path="/logs/"
                element={<BaseScreen active_tab={"Logs"} />}
              />
            </Route>

            {/* <Route element={<RequireAuth allowedRoles={[ROLES.Editor]} />}>
            <Route path="editor" element={<Editor />} />
          </Route> */}

            {/* <Route element={<RequireAuth allowedRoles={[ROLES.Admin]} />}>
            <Route path="admin" element={<Admin />} />
          </Route> */}

            {/* <Route element={<RequireAuth allowedRoles={[ROLES.Editor, ROLES.Admin]} />}>
            <Route path="lounge" element={<Lounge />} />
          </Route> */}
          </Route>

          {/* catch all */}
          {/* <Route path="*" element={<Missing />} /> */}
        </Route>
      </Routes>
    </div>
  );
}

export default App;
