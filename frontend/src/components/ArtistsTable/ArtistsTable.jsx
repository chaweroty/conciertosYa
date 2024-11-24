import React, { useState } from "react";

const ArtistsTable = () => {
  const artists = [
    { name: "Artist A", genre: "Rock", popularity: 95 },
    { name: "Artist B", genre: "Pop", popularity: 88 },
  ];

  const [selectedArtist, setSelectedArtist] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

  const openEditModal = (artist) => {
    setSelectedArtist(artist);
    setIsEditModalOpen(true);
  };

  const openDeleteModal = (artist) => {
    setSelectedArtist(artist);
    setIsDeleteModalOpen(true);
  };

  const closeModal = () => {
    setSelectedArtist(null);
    setIsEditModalOpen(false);
    setIsDeleteModalOpen(false);
  };

  const handleDelete = () => {
    console.log("Artist deleted:", selectedArtist);
    closeModal();
  };

  const handleEdit = (event) => {
    event.preventDefault();
    console.log("Artist updated:", selectedArtist);
    closeModal();
  };

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-2xl font-bold mb-4">Artists</h1>
      <div className="overflow-x-auto shadow-md sm:rounded-lg">
        <table className="min-w-full divide-y divide-gray-200">
          <thead>
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Name
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Genre
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Popularity
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Action
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {artists.map((artist, index) => (
              <tr key={index}>
                <td className="px-6 py-4 whitespace-nowrap">{artist.name}</td>
                <td className="px-6 py-4 whitespace-nowrap">{artist.genre}</td>
                <td className="px-6 py-4 whitespace-nowrap">{artist.popularity}</td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <button
                    onClick={() => openEditModal(artist)}
                    className="px-4 py-2 font-medium text-white bg-blue-600 rounded-md hover:bg-blue-500 focus:outline-none"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => openDeleteModal(artist)}
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
            <h2 className="text-lg font-bold mb-4">Edit Artist</h2>
            <form onSubmit={handleEdit}>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Name</label>
                <input
                  type="text"
                  value={selectedArtist.name}
                  onChange={(e) =>
                    setSelectedArtist({ ...selectedArtist, name: e.target.value })
                  }
                  className="w-full p-2 border rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Genre</label>
                <input
                  type="text"
                  value={selectedArtist.genre}
                  onChange={(e) =>
                    setSelectedArtist({
                      ...selectedArtist,
                      genre: e.target.value,
                    })
                  }
                  className="w-full p-2 border rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-2">Popularity</label>
                <input
                  type="number"
                  value={selectedArtist.popularity}
                  onChange={(e) =>
                    setSelectedArtist({
                      ...selectedArtist,
                      popularity: parseInt(e.target.value),
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
              Are you sure you want to delete <b>{selectedArtist.name}</b>?
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

export default ArtistsTable;
