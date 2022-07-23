package vn.savvycom.slackprovider.service;

import vn.savvycom.slackprovider.domain.entity.Workspace;

import java.util.List;

public interface IWorkspaceService {
    Workspace findById(String id);

    List<Workspace> findAll();

    void save(Workspace workspace);

    void delete(String id);
}
