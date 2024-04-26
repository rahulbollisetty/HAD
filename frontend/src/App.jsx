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
import AllPatientList from "./pages/PatientScreen/components/AllPatientList";

import { DoctorStaffScreen } from "./pages/DoctorStaffScreen/DoctorStaffScreen";

import AddRecords from "./pages/PatientScreen/components/AddRecords";
import AddAppointmentForm from "./pages/PatientScreen/forms/AddAppointmentForm";
import CalendarScreen from "./pages/calendar/CalendarScreen";
import SettingsScreen from "./pages/Settings/SettingsScreen";

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
          {/* <Route path="linkpage" element={<LinkPage />} /> */}
          <Route path="unauthorized" element={<Unauthorized />} />
          <Route path="/calendar" element={<CalendarScreen />} />
          <Route path="/settings" element={<SettingsScreen />} />

          <Route path="/staffList" element={<DoctorStaffScreen />} />

          {/* we want to protect these routes */}
          <Route element={<PersistLogin />}>
            <Route element={<RequireAuth allowedRoles={["DOCTOR"]} />}>
              <Route
                path="/patientScreen/:id"
                element={<PatientScreen tab={"Past Records"} />}
              />
              <Route path="/" element={<AllPatientList />} />
              <Route
                path="/patientScreen/:id/appointment/:appid"
                element={<PatientScreen />}
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
