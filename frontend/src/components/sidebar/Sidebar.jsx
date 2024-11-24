import { HomeIcon, UserGroupIcon, ChevronDownIcon } from "@heroicons/react/24/outline";
import { BsCalendar3WeekFill } from "react-icons/bs";
import { FaMapLocationDot } from "react-icons/fa6";
import { FaPerson } from "react-icons/fa6";
import { useState } from "react";

function Sidebar({ setActiveContent }) {
  const [dropdowns, setDropdowns] = useState({
    users: false,
  });

  const toggleDropdown = (section) => {
    setDropdowns((prevState) => ({
      ...prevState,
      [section]: !prevState[section],
    }));
  };

  const menuItems = [
    {
      name: "Dashboard",
      icon: <HomeIcon className="h-6 w-6" />,
      content: "dashboard",
    },
    {
      name: "Usuarios",
      icon: <UserGroupIcon className="h-6 w-6" />,
      content: "users",
      hasDropdown: true, // Indica si tiene un dropdown
      dropdownItems: [
        { name: "Lista de usuarios", content: "userList" },
      ],
    },
    {
      name: "Eventos",
      icon: <BsCalendar3WeekFill  className="h-6 w-6" />,
      content: "Eventos",
      hasDropdown: true, // Indica si tiene un dropdown
      dropdownItems: [
        { name: "Lista de eventos", content: "eventList" },
      ],
    },
    {
      name: "Lugares",
      icon: <FaMapLocationDot className="h-6 w-6" />,
      content: "Eventos",
      hasDropdown: true, // Indica si tiene un dropdown
      dropdownItems: [
        { name: "Lista de lugares", content: "placesList" },
      ],
    },
    {
      name: "Artistas",
      icon: <FaPerson className="h-6 w-6" />,
      content: "Eventos",
      hasDropdown: true, // Indica si tiene un dropdown
      dropdownItems: [
        { name: "Lista de artistas", content: "artistList" },
      ],
    },
  ];

  return (
    <div className="h-full bg-gray-800 text-white w-full">
      {/* Título del Sidebar */}
      <div className="p-6 border-b border-gray-700">
        <h2 className="text-2xl font-bold">Admin Panel</h2>
      </div>

      {/* Lista de enlaces */}
      <ul className="mt-4 space-y-2">
        {menuItems.map((item, index) => (
          <li key={index}>
            {/* Enlace principal */}
            <div
              className={`flex items-center justify-between p-3 hover:bg-gray-700 rounded cursor-pointer transition-colors ${
                item.hasDropdown ? "group" : ""
              }`}
              onClick={() =>
                item.hasDropdown ? toggleDropdown(item.content) : setActiveContent(item.content)
              }
            >
              <div className="flex items-center">
                {/* Icono */}
                <div className="mr-3">{item.icon}</div>
                {/* Nombre del enlace */}
                <span className="text-lg">{item.name}</span>
              </div>
              {item.hasDropdown && (
                <ChevronDownIcon
                  className={`h-5 w-5 transition-transform ${
                    dropdowns[item.content] ? "rotate-180" : ""
                  }`}
                />
              )}
            </div>

            {/* Dropdown */}
            {item.hasDropdown && dropdowns[item.content] && (
              <ul className="mt-1 space-y-1 pl-6">
                {item.dropdownItems.map((dropdownItem, idx) => (
                  <li
                    key={idx}
                    className="flex items-center p-2 hover:bg-gray-700 rounded cursor-pointer transition-colors"
                    onClick={() => setActiveContent(dropdownItem.content)}
                  >
                    <span className="text-sm">{dropdownItem.name}</span>
                  </li>
                ))}
              </ul>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Sidebar;
