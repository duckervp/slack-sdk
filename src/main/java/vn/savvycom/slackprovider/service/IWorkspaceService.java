package vn.savvycom.slackprovider.service;

import vn.savvycom.slackprovider.domain.entity.Workspace;

import java.util.List;

public interface IWorkspaceService {
    Workspace findActiveWorkspaceById(String id);

    List<Workspace> findAllActiveWorkspace();

    void save(Workspace workspace);

    void delete(String id);
}
