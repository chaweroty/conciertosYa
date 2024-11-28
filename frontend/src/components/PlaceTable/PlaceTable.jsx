import React, { useState } from "react";

const PlaceTable = () => {
  const [places, setPlaces] = useState([
    { name: "Stadium A", location: "City X", capacity: 50000 },
    { name: "Arena B", location: "City Y", capacity: 20000 },
  ]);

  const [selectedPlace, setSelectedPlace] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [newPlace, setNewPlace] = useState({
    name: "",
    location: "",
    capacity: "",
  });

  const openEditModal = (place) => {
    setSelectedPlace(place);
    setIsEditModalOpen(true);
  };

  const openDeleteModal = (place) => {
    setSelectedPlace(place);
    setIsDeleteModalOpen(true);
  };

  const openCreateModal = () => {
    setNewPlace({ name: "", location: "", capacity: "" });
    setIsCreateModalOpen(true);
  };

  const closeModal = () => {
    setSelectedPlace(null);
    setIsEditModalOpen(false);
    setIsDeleteModalOpen(false);
    setIsCreateModalOpen(false);
  };

  const handleDelete = () => {
    setPlaces(places.filter((place) => place !== selectedPlace));
    closeModal();
  };

  const handleEdit = (event) => {
    event.preventDefault();
    setPlaces(
      places.map((place) =>
        place === selectedPlace ? { ...selectedPlace } : place
      )
    );
    closeModal();
  };

  const handleCreate = (event) => {
    event.preventDefault();
    setPlaces([...places, { ...newPlace, capacity: parseInt(newPlace.capacity) }]);
    closeModal();
  };

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-2xl font-bold mb-4">Places</h1>
      <button
        onClick={openCreateModal}
        className="mb-4 px-4 py-2 font-medium text-white bg-green-600 rounded-md hover:bg-green-500 focus:outline-none"
      >
        Add Place
      </button>
      <div className="overflow-x-auto shadow-md sm:rounded-lg">
        <table className="min-w-full divide-y divide-gray-200">
          <thead>
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Name
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Location
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Capacity
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Action
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {places.map((place, index) => (
              <tr key={index}>
                <td className="px-6 py-4 whitespace-nowrap">{place.name}</td>
                <td className="px-6 py-4 whitespace-nowrap">{place.location}</td>
                <td className="px-6 py-4 whitespace-nowrap">{place.capacity}</td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <button
                    onClick={() => openEditModal(place)}
                    className="px-4 py-2 font-medium text-white bg-blue-600 rounded-md hover:bg-blue-500 focus:outline-none"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => openDeleteModal(place)}
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

      {/* Create Modal */}
      {isCreateModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3">
            <h2 className="text-lg font-bold mb-4">Add New Place</h2>
            <form onSubmit={handleCreate}>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Name</label>
                <input
                  type="text"
                  value={newPlace.name}
                  onChange={(e) =>
                    setNewPlace({ ...newPlace, name: e.target.value })
                  }
                  className="w-full p-2 border rounded"
                  placeholder="Place Name"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Location</label>
                <input
                  type="text"
                  value={newPlace.location}
                  onChange={(e) =>
                    setNewPlace({ ...newPlace, location: e.target.value })
                  }
                  className="w-full p-2 border rounded"
                  placeholder="Location"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Capacity</label>
                <input
                  type="number"
                  value={newPlace.capacity}
                  onChange={(e) =>
                    setNewPlace({ ...newPlace, capacity: e.target.value })
                  }
                  className="w-full p-2 border rounded"
                  placeholder="Capacity"
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
                  className="px-4 py-2 bg-green-600 text-white rounded"
                >
                  Create
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Edit Modal */}
      {isEditModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3">
            <h2 className="text-lg font-bold mb-4">Edit Place</h2>
            <form onSubmit={handleEdit}>
              {/* Similar to your original code */}
            </form>
          </div>
        </div>
      )}

      {/* Delete Modal */}
      {isDeleteModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3 text-center">
            <h2 className="text-lg font-bold mb-4">Confirm Deletion</h2>
            <p className="mb-4">
              Are you sure you want to delete <b>{selectedPlace.name}</b>?
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

export default PlaceTable;