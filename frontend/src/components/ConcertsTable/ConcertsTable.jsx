import React, { useState } from "react";

const ConcertsTable = () => {
  const events = [
    {
      id: 1,
      name: "Music Fest 2024",
      date: "2024-12-15",
      location: "Central Park",
      image: "https://via.placeholder.com/150/0000FF/808080?text=Music+Fest",
    },
    {
      id: 2,
      name: "Art Exhibition",
      date: "2024-11-25",
      location: "Modern Art Museum",
      image: "https://via.placeholder.com/150/FF0000/FFFFFF?text=Art+Exhibit",
    },
  ];

  const [selectedEvent, setSelectedEvent] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

  const openEditModal = (event) => {
    setSelectedEvent(event);
    setIsEditModalOpen(true);
  };

  const openDeleteModal = (event) => {
    setSelectedEvent(event);
    setIsDeleteModalOpen(true);
  };

  const closeModal = () => {
    setSelectedEvent(null);
    setIsEditModalOpen(false);
    setIsDeleteModalOpen(false);
  };

  const handleDelete = () => {
    console.log("Event deleted:", selectedEvent);
    closeModal();
  };

  const handleEdit = (event) => {
    event.preventDefault();
    console.log("Event updated:", selectedEvent);
    closeModal();
  };

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-2xl font-bold mb-4">Events</h1>
      <div className="overflow-x-auto shadow-md sm:rounded-lg">
        <table className="min-w-full divide-y divide-gray-200">
          <thead>
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Event
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Date
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Location
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Action
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {events.map((event) => (
              <tr key={event.id}>
                <th
                  scope="row"
                  className="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap"
                >
                  <img
                    className="w-10 h-10 rounded-full"
                    src={event.image}
                    alt={`${event.name} image`}
                  />
                  <div className="ps-3">
                    <div className="text-base font-semibold">{event.name}</div>
                    <div className="font-normal text-gray-500">{event.date}</div>
                  </div>
                </th>
                <td className="px-6 py-4 whitespace-nowrap">{event.date}</td>
                <td className="px-6 py-4 whitespace-nowrap">{event.location}</td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <button
                    onClick={() => openEditModal(event)}
                    className="px-4 py-2 font-medium text-white bg-blue-600 rounded-md hover:bg-blue-500 focus:outline-none"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => openDeleteModal(event)}
                    className="ml-2 px-4 py-2 font-medium text-white bg-red-600 rounded-md hover:bg-red-500 focus:outline-none"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {isEditModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3">
            <h2 className="text-lg font-bold mb-4">Edit Event</h2>
            <form onSubmit={handleEdit}>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Name</label>
                <input
                  type="text"
                  value={selectedEvent.name}
                  onChange={(e) =>
                    setSelectedEvent({ ...selectedEvent, name: e.target.value })
                  }
                  className="w-full p-2 border rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Date</label>
                <input
                  type="date"
                  value={selectedEvent.date}
                  onChange={(e) =>
                    setSelectedEvent({ ...selectedEvent, date: e.target.value })
                  }
                  className="w-full p-2 border rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Location</label>
                <input
                  type="text"
                  value={selectedEvent.location}
                  onChange={(e) =>
                    setSelectedEvent({
                      ...selectedEvent,
                      location: e.target.value,
                    })
                  }
                  className="w-full p-2 border rounded"
                />
              </div>
              <div className="flex justify-end">
                <button
                  type="button"
                  onClick={closeModal}
                  className="px-4 py-2 bg-gray-200 rounded mr-2"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded"
                >
                  Save Changes
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {isDeleteModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3 text-center">
            <h2 className="text-lg font-bold mb-4">Confirm Deletion</h2>
            <p className="mb-4">
              Are you sure you want to delete <b>{selectedEvent.name}</b>?
            </p>
            <div className="flex justify-center">
              <button
                onClick={closeModal}
                className="px-4 py-2 bg-gray-200 rounded mr-2"
              >
                Cancel
              </button>
              <button
                onClick={handleDelete}
                className="px-4 py-2 bg-red-600 text-white rounded"
              >
                Delete
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ConcertsTable;
