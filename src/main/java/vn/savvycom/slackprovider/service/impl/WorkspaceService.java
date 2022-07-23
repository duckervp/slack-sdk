package vn.savvycom.slackprovider.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.savvycom.slackprovider.domain.entity.Workspace;
import vn.savvycom.slackprovider.repository.WorkspaceRepository;
import vn.savvycom.slackprovider.service.IWorkspaceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService implements IWorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    @Override
    public Workspace findById(String id) {
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found any workspace with id " + id));
    }

    @Override
    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }

    @Override
    public void save(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

    @Override
    public void delete(String id) {
        Workspace workspace = findById(id);
        workspace.setActive(false);
        save(workspace);
    }
}
